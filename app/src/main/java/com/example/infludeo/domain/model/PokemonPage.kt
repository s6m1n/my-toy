package com.example.infludeo.domain.model

data class PokemonPage(
    val count: Int,
    val next: String?,
    val previous: String?,
    val pokemonPageItems: List<PokemonPageItem>,
)
