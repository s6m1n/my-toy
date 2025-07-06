package com.example.bingtoy.domain.repository

import com.example.bingtoy.data.local.PokemonDao
import com.example.bingtoy.data.local.model.toDomain
import com.example.bingtoy.data.local.model.toEntity
import com.example.bingtoy.data.remote.PokemonApiService
import com.example.bingtoy.data.remote.model.toDomain
import com.example.bingtoy.domain.model.DeleteResult
import com.example.bingtoy.domain.model.InsertResult
import com.example.bingtoy.domain.model.PokemonDetail
import com.example.bingtoy.domain.model.PokemonPage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
        ): Flow<Result<PokemonPage>> =
            flow {
                emit(
                    runCatching {
                        pokemonApiService.getPokemons(offset = offset, limit = limit).toDomain()
                    },
                )
            }

        override suspend fun getPokemonDetail(id: Long): Flow<PokemonDetail> =
            flow {
                coroutineScope {
                    val localDeferred = async { dao.getById(id) }
                    val remoteDeferred =
                        async { runCatching { pokemonApiService.getPokemonDetail(id.toString()) } }
                    localDeferred.await()
                        ?.let { emit(it.toDomain()) }
                        ?: run {
                            remoteDeferred.await()
                                .onFailure { throw it }
                                .onSuccess { emit(it.toDomain()) }
                        }
                }
            }

        override suspend fun getAllFavoritePokemon(): Flow<List<PokemonDetail>> = dao.getAll().map { it.map { it.toDomain() } }

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
