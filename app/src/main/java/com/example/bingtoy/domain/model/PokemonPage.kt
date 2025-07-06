package com.example.bingtoy.domain.model

data class PokemonPage(
    val nextOffset: Int?,
    val pokemonPageItems: List<PokemonPageItem>,
)
