package com.example.infludeo.presentation.list

import com.example.infludeo.domain.model.PokemonPage
import com.example.infludeo.domain.model.PokemonPageItem

data class PokemonListUiModel(
    val nextOffset: Int?,
    val pokemons: List<PokemonPageItem>,
) {
    val hasNextPage: Boolean get() = nextOffset != null
}

fun PokemonPage.toUiModel() =
    PokemonListUiModel(
        nextOffset = nextOffset,
        pokemons = pokemonPageItems,
    )
