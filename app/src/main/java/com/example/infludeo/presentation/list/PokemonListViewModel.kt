package com.example.infludeo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infludeo.domain.repository.PokemonRepository
import com.example.infludeo.presentation.list.PokemonListUiModel.Companion.emptyPokemonListUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

        private var pageJobs: MutableMap<Int, Job> = mutableMapOf()

        init {
            fetchPokemons()
        }

        private fun fetchPokemons() {
            if (pageJobs[INITIAL_OFFSET]?.isActive == true) return
            pageJobs[INITIAL_OFFSET] =
                viewModelScope.launch {
                    fetchAndMergeSearchResults(emptyPokemonListUiModel)
                }
        }

        fun fetchNextPage() {
            viewModelScope.launch {
                val state = uiState.value
                if (state is PokemonListUiState.Success && state.data.hasNextPage) {
                    fetchAndMergeSearchResults(state.data)
                }
            }
        }

        private suspend fun fetchAndMergeSearchResults(pokemons: PokemonListUiModel) {
            val offset = pokemons.nextOffset!!
            if (pageJobs[offset / LIMIT]?.isActive != true) {
                pageJobs[offset / LIMIT] =
                    coroutineScope {
                        launch {
                            pokemonRepository.getPokemons(
                                offset = offset,
                                limit = LIMIT,
                            ).catch {
                                _uiState.emit(PokemonListUiState.Error(it.message.orEmpty()))
                            }.collect {
                                _uiState.emit(
                                    PokemonListUiState.Success(pokemons.mergeWith(it.toUiModel())),
                                )
                            }
                        }
                    }
            }
        }

        companion object {
            private const val INITIAL_OFFSET = 0
            private const val LIMIT = 18
        }
    }
