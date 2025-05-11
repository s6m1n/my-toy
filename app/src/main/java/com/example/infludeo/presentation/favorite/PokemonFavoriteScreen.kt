package com.example.infludeo.presentation.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.infludeo.presentation.PokemonAppState
import com.example.infludeo.presentation.list.PokemonListViewModel

@Composable
fun PokemonFavoriteScreen(
    appState: PokemonAppState,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val favoriteUiState = viewModel.favoritePokemonList.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.fetchFavoritePokemons()
    }
    PokemonFavoriteGrid(
        favoriteUiState,
        onItemClicked = { appState.moveToFavoriteDetailScreen(it) },
    )
}
