package com.example.infludeo.domain.model

data class PokemonPage(
    val nextOffset: Int?,
    val pokemonPageItems: List<PokemonPageItem>,
)
