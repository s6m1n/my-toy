package com.example.bingtoy.presentation.detail.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bingtoy.data.util.CacheImageManager
import com.example.bingtoy.domain.model.DeleteResult
import com.example.bingtoy.domain.model.InsertResult
import com.example.bingtoy.domain.repository.PokemonRepository
import com.example.bingtoy.presentation.detail.ui.PokemonDetailUiState
import com.example.bingtoy.presentation.navigation.ARGUMENT_POKEMON_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val pokemonRepository: PokemonRepository,
        private val cacheImageManager: CacheImageManager,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Idle)
        val uiState = _uiState.asStateFlow()

        private val _insertFavoriteEvent = MutableSharedFlow<InsertResult>()
        val insertFavoriteEvent = _insertFavoriteEvent.asSharedFlow()

        private val _deleteFavoriteEvent = MutableSharedFlow<DeleteResult>()
        val deleteFavoriteEvent = _deleteFavoriteEvent.asSharedFlow()

        private val _errorEvent = MutableSharedFlow<Exception>()
        val errorEvent = _errorEvent.asSharedFlow()

        init {
            val id = savedStateHandle.get<Long>(ARGUMENT_POKEMON_ID)
            if (id != null) {
                fetchPokemonDetail(id)
            }
        }

        private fun fetchPokemonDetail(id: Long) {
            viewModelScope.launch {
                pokemonRepository.getPokemonDetail(id = id)
                    .catch {
                        _errorEvent.emit(Exception(it))
                    }.collect {
                        _uiState.emit(PokemonDetailUiState.Success(it))
                    }
            }
        }

        fun deleteFavoritePokemon(id: Long) {
            viewModelScope.launch {
                pokemonRepository.deleteFavoritePokemon(id).catch {
                    _errorEvent.emit(Exception(it))
                }.collect {
                    _deleteFavoriteEvent.emit(it)
                }
            }
        }

        fun addFavoritePokemon(context: Context) {
            if (uiState.value is PokemonDetailUiState.Success) {
                val pokemon = (uiState.value as PokemonDetailUiState.Success).data
                viewModelScope.launch {
                    val file =
                        cacheImageManager.downloadImageFromUrl(
                            context,
                            pokemon.imageUrl,
                            "pokemon_${pokemon.name}.png",
                        )
                    file?.let { it ->
                        pokemonRepository.addFavoritePokemon(pokemon.copy(imagePath = it.absolutePath))
                            .catch {
                                _errorEvent.emit(Exception(it))
                            }.collect {
                                _insertFavoriteEvent.emit(it)
                            }
                    }
                }
            }
        }
    }
