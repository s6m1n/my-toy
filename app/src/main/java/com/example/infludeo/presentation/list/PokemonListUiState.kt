package com.example.infludeo.presentation.list

sealed interface PokemonListUiState {
    data object Idle : PokemonListUiState

    data class Success(val data: PokemonListUiModel) : PokemonListUiState

    data class Error(val message: String) : PokemonListUiState
}
