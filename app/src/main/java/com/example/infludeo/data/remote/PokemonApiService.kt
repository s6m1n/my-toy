package com.example.infludeo.data.remote

import com.example.infludeo.data.model.PokemonDetailResponse
import com.example.infludeo.data.model.PokemonPageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET(POKEMON)
    suspend fun getPokemons(
        @Query(OFFSET_PARAM) offset: Int,
        @Query(LIMIT_PARAM) limit: Int,
    ): PokemonPageResponse

    @GET(POKEMON_DETAIL)
    suspend fun getPokemonDetail(
        @Path(ID_OR_NAME_PARAM) idOrName: String,
    ): PokemonDetailResponse

    companion object {
        private const val POKEMON = "pokemon"
        private const val OFFSET_PARAM = "offset"
        private const val LIMIT_PARAM = "limit"
        private const val ID_OR_NAME_PARAM = "idOrName"
        private const val POKEMON_DETAIL = "$POKEMON/{$ID_OR_NAME_PARAM}"
    }
}
