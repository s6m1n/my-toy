package com.example.infludeo.data.remote.model

import com.example.infludeo.domain.model.PokemonPageItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonPageItemResponse(
    @SerialName("name") val name: String?,
    @SerialName("url") val url: String,
)

fun PokemonPageItemResponse.toDomain(): PokemonPageItem {
    val id = url.extractId()
    return PokemonPageItem(
        id = id,
        name = name,
        imageUrl = id.toImageUrl(),
    )
}

private fun String.extractId(): Long =
    this.trimEnd('/')
        .split("/")
        .last()
        .toLong()

fun Long.toImageUrl() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$this.png"
