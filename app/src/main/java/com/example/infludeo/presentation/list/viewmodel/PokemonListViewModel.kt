package com.example.infludeo.presentation.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infludeo.domain.model.PokemonDetail
import com.example.infludeo.domain.repository.PokemonRepository
import com.example.infludeo.presentation.list.PokemonListUiModel
import com.example.infludeo.presentation.list.PokemonListUiModel.Companion.emptyPokemonListUiModel
import com.example.infludeo.presentation.list.toUiModel
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
        private val _pokemonList = MutableStateFlow(PokemonListUiModel(0, emptyList())) // 서버에서 가져온 원형
        val pokemonList = _pokemonList.asStateFlow()

        private val _favoritePokemonList =
            MutableStateFlow<List<PokemonDetail>>(emptyList()) // room 에서 가져온 원형
        val favoritePokemonList = _favoritePokemonList.asStateFlow()

        private var pageJobs: MutableMap<Int, Job> = mutableMapOf()

        private val _errorEvent = MutableSharedFlow<Exception>()
        val errorEvent = _errorEvent.asSharedFlow()

        init {
            fetchFavoritePokemons()
            fetchPokemons()
        }

        fun fetchFavoritePokemons() {
            viewModelScope.launch {
                pokemonRepository.getAllFavoritePokemon().collect { favoritePokemonDetailList ->

                    val favoriteIds = favoritePokemonDetailList.map { it.id }.toSet()
                    _favoritePokemonList.emit(favoritePokemonDetailList)

                    val currentList = _pokemonList.value.pokemons
                    val updatedList = currentList.map { it.copy(isFavorite = it.id in favoriteIds) }

                    _pokemonList.value = _pokemonList.value.copy(pokemons = updatedList)
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
                    result.onSuccess { nextPagePokemon ->
                        val favoriteIds = _favoritePokemonList.value.map { it.id }.toSet()
                        val newList = nextPagePokemon.pokemonPageItems.map { it.copy(isFavorite = it.id in favoriteIds) }
                        val newModel =
                            nextPagePokemon.copy(
                                pokemonPageItems = newList,
                            )

                        _pokemonList.emit(
                            pokemons.mergeWith(newModel.toUiModel()),
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
