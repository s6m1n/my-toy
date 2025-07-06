package com.example.bingtoy.data.remote.model

import com.example.bingtoy.domain.model.PokemonDetail
import com.example.bingtoy.domain.model.PokemonType
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    val id: Long,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeSlotResponse>,
)

@Serializable
data class PokemonTypeSlotResponse(
    val slot: Int,
    val type: PokemonTypeResponse,
)

@Serializable
data class PokemonTypeResponse(
    val name: String,
    val url: String,
)

fun PokemonDetailResponse.toDomain() =
    PokemonDetail(
        id = id,
        name = name,
        imageUrl = id.toImageUrl(),
        height = height,
        weight = weight,
        types = types.sortedBy { it.slot }.map { it.toDomain() },
    )

fun PokemonTypeSlotResponse.toDomain() =
    PokemonType(
        name = type.name,
        url = type.url,
    )
