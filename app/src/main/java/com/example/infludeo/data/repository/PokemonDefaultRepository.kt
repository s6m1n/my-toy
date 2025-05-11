package com.example.infludeo.data.repository

import com.example.infludeo.data.local.PokemonDao
import com.example.infludeo.data.model.toDomain
import com.example.infludeo.data.model.toEntity
import com.example.infludeo.data.remote.PokemonApiService
import com.example.infludeo.domain.model.DeleteResult
import com.example.infludeo.domain.model.InsertResult
import com.example.infludeo.domain.model.PokemonDetail
import com.example.infludeo.domain.model.PokemonPage
import com.example.infludeo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonDefaultRepository
    @Inject
    constructor(
        private val pokemonApiService: PokemonApiService,
        private val dao: PokemonDao,
    ) : PokemonRepository {
        override suspend fun getPokemons(
            offset: Int,
            limit: Int,
        ): Flow<PokemonPage> =
            flow {
                emit(pokemonApiService.getPokemons(offset = offset, limit = limit).toDomain())
            }

        override suspend fun getPokemonDetail(id: Long): Flow<PokemonDetail> =
            flow {
                val cached = dao.getById(id)
                cached?.let { emit(it.toDomain()) } ?: run {
                    try {
                        val remote = pokemonApiService.getPokemonDetail(id.toString())
                        emit(remote.toDomain())
                    } catch (e: Exception) {
                        // 예외 처리
                    }
                }
            }

        override suspend fun getAllFavoritePokemon(): Flow<List<PokemonDetail>> =
            flow {
                emit(dao.getAll().map { it.toDomain() })
            }

        override suspend fun addFavoritePokemon(pokemon: PokemonDetail): Flow<InsertResult> =
            flow {
                emit(dao.insertWithLimit(pokemon.toEntity()))
            }

        override suspend fun deleteFavoritePokemon(id: Long): Flow<DeleteResult> =
            flow {
                emit(
                    dao.deleteIfExists(id),
                )
            }
    }
