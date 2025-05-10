package com.example.infludeo.presentation.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infludeo.domain.repository.PokemonRepository
import com.example.infludeo.presentation.detail.ui.PokemonDetailUiState
import com.example.infludeo.presentation.navigation.ARGUMENT_POKEMON_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val pokemonRepository: PokemonRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Idle)
        val uiState = _uiState.asStateFlow()

        init {
            val id = savedStateHandle.get<Long>(ARGUMENT_POKEMON_ID)
            if (id != null) {
                fetchPokemonDetail(id)
            }
        }

        private fun fetchPokemonDetail(id: Long) {
            viewModelScope.launch {
                pokemonRepository.getPokemonDetail(idOrName = id.toString()).collect {
                    _uiState.emit(PokemonDetailUiState.Success(it))
                }
            }
        }
    }
