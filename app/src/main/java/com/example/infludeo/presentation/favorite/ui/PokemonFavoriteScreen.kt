package com.example.infludeo.presentation.favorite.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.infludeo.presentation.PokemonAppState
import com.example.infludeo.presentation.list.viewmodel.PokemonListViewModel

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
