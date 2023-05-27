package com.example.foody.ui.main

import Category
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.model.Recipe
import com.example.foody.ui.utils.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _searchTextState: MutableStateFlow<String> = MutableStateFlow("")
    val searchTextState: StateFlow<String> = _searchTextState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _meals: MutableState<List<Recipe>> = mutableStateOf(emptyList())
    val meals: State<List<Recipe>> = _meals

    val favouriteMeals: Flow<List<Recipe>> = mainRepository.loadFavouriteRecipes()

    val categories: Flow<List<Category>> = mainRepository.loadCategories()

    private var searchJob: Job? = null

    init {
        observeSearchTextChanges()
    }

    fun onSearchTextChange(text: String) {
        _searchTextState.value = text
    }

    fun onSetCategory(categoryName: String) {
        viewModelScope.launch {
            mainRepository.loadMealsByCategory(categoryName).collect {
                _meals.value = it

            }
        }
    }

    fun addFavouriteMeal(id: Int) {
        val favourite = _meals.value.find { r -> r.apiId == id }
        if (favourite != null) {
            viewModelScope.launch {
                mainRepository.insertFavouriteMeal(favourite)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchTextChanges() {
        searchJob?.cancel() // Cancel any existing search job

        searchJob = viewModelScope.launch {
            _searchTextState.debounce(500) // Debounce for 500 milliseconds
                .collectLatest { searchText ->
                    _isSearching.value = true
                    _isLoading.value = true

                    // Call repository with the updated searchText
                    mainRepository.loadMeals(searchText, {}, {})
                        .collect { meals ->
                            _meals.value = meals
                        }

                    _isLoading.value = false
                    _isSearching.value = false
                }
        }
    }
}