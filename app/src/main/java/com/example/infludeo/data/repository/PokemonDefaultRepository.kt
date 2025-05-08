package com.example.infludeo.data.repository

import com.example.infludeo.data.model.toDomain
import com.example.infludeo.data.remote.PokemonApiService
import com.example.infludeo.domain.model.PokemonPage
import com.example.infludeo.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonDefaultRepository
    @Inject
    constructor(
        private val pokemonApiService: PokemonApiService,
    ) : PokemonRepository {
        override suspend fun getPokemons(
            offset: Int,
            limit: Int,
        ): Flow<PokemonPage> =
            flow {
                emit(pokemonApiService.getPokemons(offset = offset, limit = limit).toDomain())
            }
    }
