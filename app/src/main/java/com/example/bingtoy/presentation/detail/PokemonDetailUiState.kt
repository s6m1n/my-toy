package com.example.bingtoy.presentation.detail

import com.example.bingtoy.domain.model.PokemonDetail

sealed interface PokemonDetailUiState {
    data object Idle : PokemonDetailUiState

    data class Success(val data: PokemonDetail) : PokemonDetailUiState

    data class Error(val message: String) : PokemonDetailUiState
}
