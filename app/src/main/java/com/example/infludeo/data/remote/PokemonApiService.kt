package com.example.infludeo.data.remote

import com.example.infludeo.data.model.PokemonPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET(POKEMON)
    suspend fun getPokemons(
        @Query(OFFSET_PARAM) offset: Int,
        @Query(LIMIT_PARAM) limit: Int,
    ): PokemonPageResponse

    companion object {
        private const val POKEMON = "pokemon"
        private const val OFFSET_PARAM = "offset"
        private const val LIMIT_PARAM = "limit"
    }
}
