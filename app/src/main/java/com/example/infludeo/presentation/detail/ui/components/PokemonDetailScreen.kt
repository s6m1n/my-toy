package com.example.infludeo.presentation.detail.ui.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.infludeo.presentation.detail.ui.PokemonDetailUiState
import com.example.infludeo.presentation.detail.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(viewModel: PokemonDetailViewModel) {
    val detailUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    when (detailUiState) {
        is PokemonDetailUiState.Idle -> {}
        is PokemonDetailUiState.Error -> {}
        is PokemonDetailUiState.Success -> PokemonDetailContent(detailUiState.data)
    }
}
