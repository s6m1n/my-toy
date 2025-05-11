package com.example.infludeo.domain.repository

import com.example.infludeo.domain.model.DeleteResult
import com.example.infludeo.domain.model.InsertResult
import com.example.infludeo.domain.model.PokemonDetail
import com.example.infludeo.domain.model.PokemonPage
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemons(
        offset: Int,
        limit: Int,
    ): Flow<Result<PokemonPage>>

    suspend fun getPokemonDetail(id: Long): Flow<PokemonDetail>

    suspend fun getAllFavoritePokemon(): Flow<List<PokemonDetail>>

    suspend fun addFavoritePokemon(pokemon: PokemonDetail): Flow<InsertResult>

    suspend fun deleteFavoritePokemon(id: Long): Flow<DeleteResult>
}
