package com.example.infludeo.data.model

import com.example.infludeo.domain.model.PokemonPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonPageResponse(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: String?,
    @SerialName("previous") val previous: String?,
    @SerialName("results") val results: List<PokemonPageItemResponse>,
)

fun PokemonPageResponse.toDomain() =
    PokemonPage(
        count = count,
        next = next,
        previous = previous,
        pokemonPageItems = results.map { it.toDomain() },
    )
