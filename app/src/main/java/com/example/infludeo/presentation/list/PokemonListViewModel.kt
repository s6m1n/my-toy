package com.example.infludeo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infludeo.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel
    @Inject
    constructor(
        private val pokemonRepository: PokemonRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Idle)
        val uiState = _uiState.asStateFlow()

        fun fetchPokemons() {
            viewModelScope.launch {
                pokemonRepository.getPokemons(offset = 0, limit = 18)
                    .onStart { _uiState.emit(PokemonListUiState.Loading) }
                    .catch { _uiState.emit(PokemonListUiState.Error(it.message.orEmpty())) }
                    .collect { pokemonPage ->
                        _uiState.emit(
                            PokemonListUiState.Success(
                                pokemonPage.toUiModel(),
                            ),
                        )
                    }
            }
        }
    }
