package com.example.infludeo.data.remote.model

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
        nextOffset = next?.extractNextOffset(),
        pokemonPageItems = results.map { it.toDomain() },
    )

private fun String.extractNextOffset(): Int? {
    val regex = Regex("""offset=(\d+)""")
    val match = regex.find(this)
    return match?.groupValues?.get(1)?.toIntOrNull()
}
