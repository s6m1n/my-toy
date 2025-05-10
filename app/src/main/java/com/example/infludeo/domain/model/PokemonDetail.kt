package com.example.infludeo.domain.model

data class PokemonDetail(
    val id: Long,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val imageUrl: String,
)

data class PokemonType(
    val name: String,
    val url: String,
)
