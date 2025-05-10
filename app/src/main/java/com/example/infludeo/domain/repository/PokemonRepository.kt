package com.example.infludeo.domain.repository

import com.example.infludeo.domain.model.PokemonDetail
import com.example.infludeo.domain.model.PokemonPage
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemons(
        offset: Int,
        limit: Int,
    ): Flow<PokemonPage>

    suspend fun getPokemonDetail(idOrName: String): Flow<PokemonDetail>
}
