package com.example.infludeo.presentation.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.infludeo.R
import com.example.infludeo.presentation.PokemonAppState

@Composable
fun PokemonListScreen(
    appState: PokemonAppState,
    viewModel: PokemonListViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    when (uiState) {
        is PokemonListUiState.Error -> {
            Text(stringResource(R.string.network_connection_failed))
        }

        is PokemonListUiState.Success -> {
            PokemonGrid(
                uiState.data,
                onScrollNewPage = { viewModel.fetchNextPage() },
                onItemClicked = { appState.moveToDetailScreen(it) },
            )
        }

        PokemonListUiState.Idle -> {}
    }
}
