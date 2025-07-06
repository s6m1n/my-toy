package com.example.bingtoy.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bingtoy.R
import com.example.bingtoy.presentation.PokemonAppState
import com.example.bingtoy.presentation.common.util.showToast
import com.example.bingtoy.presentation.list.ui.PokemonGrid

@Composable
fun PokemonListScreen(
    appState: PokemonAppState,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val pokemonList = viewModel.pokemonList.collectAsStateWithLifecycle().value
    PokemonGrid(
        pokemonList,
        onScrollNewPage = { viewModel.fetchNextPage() },
        onItemClicked = { appState.moveToDetailScreen(it) },
    )

    val string = stringResource(R.string.unexpected_error)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorEvent.collect {
            showToast(string, context)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.fetchFavoritePokemons()
    }
}
