package com.example.infludeo.presentation.detail.ui

import com.example.infludeo.domain.model.PokemonDetail

sealed interface PokemonDetailUiState {
    data object Idle : PokemonDetailUiState

    data class Success(val data: PokemonDetail) : PokemonDetailUiState

    data class Error(val message: String) : PokemonDetailUiState
}
