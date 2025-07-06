package com.example.bingtoy.presentation.favoritedetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bingtoy.presentation.PokemonAppState
import com.example.bingtoy.presentation.favoritedetail.ui.PokemonFavoriteGrid
import com.example.bingtoy.presentation.list.PokemonListViewModel

@Composable
fun PokemonFavoriteScreen(
    appState: PokemonAppState,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val favoriteUiState = viewModel.favoritePokemonList.collectAsStateWithLifecycle().value
    PokemonFavoriteGrid(
        favoriteUiState,
        onItemClicked = { appState.moveToFavoriteDetailScreen(it) },
    )
}
