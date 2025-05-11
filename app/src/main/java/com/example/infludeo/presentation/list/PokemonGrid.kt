package com.example.infludeo.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PokemonGrid(
    pokemonUiModel: PokemonListUiModel,
    modifier: Modifier = Modifier,
    onScrollNewPage: () -> Unit,
    onItemClicked: (Long) -> Unit,
) {
    val gridState: LazyGridState = rememberLazyGridState()
    val isBottomReached by gridState.rememberIsBottomReached()
    if (isBottomReached) {
        onScrollNewPage()
    }
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(3),
        modifier =
            modifier.fillMaxSize()
                .background(Color.White),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(pokemonUiModel.pokemons) { item ->
            PokemonGridItem(item) { id ->
                onItemClicked(id)
            }
        }
    }
}

@Composable
private fun LazyGridState.rememberIsBottomReached(): State<Boolean> =
    remember(this) {
        derivedStateOf {
            if (layoutInfo.totalItemsCount == 0) {
                return@derivedStateOf false
            } else {
                val lastItem =
                    layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false
                return@derivedStateOf (lastItem.index == layoutInfo.totalItemsCount - 1)
            }
        }
    }
