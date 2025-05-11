package com.example.infludeo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infludeo.domain.model.PokemonDetail
import com.example.infludeo.domain.repository.PokemonRepository
import com.example.infludeo.presentation.list.PokemonListUiModel.Companion.emptyPokemonListUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel
    @Inject
    constructor(
        private val pokemonRepository: PokemonRepository,
    ) : ViewModel() {
        private val _pokemonList = MutableStateFlow(PokemonListUiModel(0, emptyList()))
        val pokemonList = _pokemonList.asStateFlow()

        private val _favoriteListUiState = MutableStateFlow<List<PokemonDetail>>(emptyList())
        val favoriteListUiState = _favoriteListUiState.asStateFlow()

        private var pageJobs: MutableMap<Int, Job> = mutableMapOf()

        private val _errorEvent = MutableSharedFlow<Exception>()
        val errorEvent = _errorEvent.asSharedFlow()

        init {
            fetchPokemons()
            fetchFavoritePokemons()
        }

        fun fetchFavoritePokemons() {
            viewModelScope.launch {
                pokemonRepository.getAllFavoritePokemon().collect {
                    _favoriteListUiState.emit(it)
                }
            }
        }

        fun fetchNextPage() {
            viewModelScope.launch {
                val pokemons = pokemonList.value
                if (pokemons.hasNextPage) {
                    fetchAndMergeSearchResults(pokemons)
                }
            }
        }

        private fun fetchPokemons() {
            if (pageJobs[INITIAL_OFFSET]?.isActive == true) return
            pageJobs[INITIAL_OFFSET] =
                viewModelScope.launch {
                    fetchAndMergeSearchResults(emptyPokemonListUiModel)
                }
        }

        private suspend fun fetchAndMergeSearchResults(pokemons: PokemonListUiModel) {
            val offset = pokemons.nextOffset!!
            val pageIndex = offset / LIMIT
            if (pageJobs[pageIndex]?.isActive != true) {
                val job = createNextPageJob(offset, pokemons)
                job.invokeOnCompletion { pageJobs.remove(pageIndex) }
                pageJobs[pageIndex] = job
            }
        }

        private suspend fun createNextPageJob(
            offset: Int,
            pokemons: PokemonListUiModel,
        ) = coroutineScope {
            launch {
                pokemonRepository.getPokemons(
                    offset = offset,
                    limit = LIMIT,
                ).collect { result ->
                    result.onSuccess {
                        _pokemonList.emit(
                            pokemons.mergeWith(it.toUiModel()),
                        )
                    }.onFailure {
                        _errorEvent.emit(Exception(it))
                    }
                }
            }
        }

        companion object {
            private const val INITIAL_OFFSET = 0
            private const val LIMIT = 18
        }
    }
