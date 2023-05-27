package com.example.foody.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.foody.ui.main.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodySearchBar(
    title: String,
    text: String,
    onTextChanged: (String) -> Unit,
    onSetCategory: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    isHome: Boolean = true,
    mainViewModel: MainViewModel
) {
    var text by rememberSaveable { mutableStateOf(text) }
    var active by rememberSaveable { mutableStateOf(false) }
    val categories by mainViewModel.categories.collectAsState(initial = listOf())
    var selectedCategory by rememberSaveable { mutableStateOf(-1) }
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
    )
    {
        Box(
            Modifier
                .semantics { isContainer = true }
                .zIndex(1f)
                .fillMaxWidth()) {
            SearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                query = text,
                onQueryChange = {
                    text = it
                    onTextChanged(it)
                },
                onSearch = {
                    active = false
                    onSearchClicked(text)
                },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = { Text("Search recipes") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            ) {}
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 10.dp),
            text = title,
            color = Color.White,
            fontWeight = FontWeight(400),
            fontSize = 40.sp
        )
        if (isHome) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(10.dp, 1.dp)
            ) {
                categories.forEachIndexed { index, category ->
                    SuggestionChip(
                        modifier = Modifier.padding(2.dp, 0.dp),
                        onClick = {
                            onSetCategory(category.strCategory)
                            selectedCategory = index
                        },
                        label = { Text(text = category.strCategory, fontSize = 15.sp) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = if (selectedCategory == index) Color.White else Color.Gray,
                            labelColor = Color.Black
                        )
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun FoodySearchBarPreview() {
//    FoodySearchBar(
//        title = "Home",
//        text = "Some random",
//        onTextChanged = {},
//        onCloseClicked = {},
//        onSearchClicked = {}
//    )
//}