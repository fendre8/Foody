package com.example.foody.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
  private val detailsRepository: DetailsRepository,
) : ViewModel() {
  private val mealIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

  @OptIn(ExperimentalCoroutinesApi::class)
  val mealDetailsFlow = mealIdSharedFlow.flatMapLatest {
    detailsRepository.getMealById(it)
  }

  fun loadMealById(id: Int) = mealIdSharedFlow.tryEmit(id)

  fun saveRecipe(recipe: Recipe) {
    viewModelScope.launch {
      detailsRepository.saveRecipe(recipe);
    }
  }
}