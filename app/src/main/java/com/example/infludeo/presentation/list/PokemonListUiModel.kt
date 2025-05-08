package com.example.infludeo.presentation.list

import com.example.infludeo.domain.model.PokemonPage
import com.example.infludeo.domain.model.PokemonPageItem

data class PokemonListUiModel(
    val pokemons: List<PokemonPageItem>,
)

fun PokemonPage.toUiModel() =
    PokemonListUiModel(
        pokemons = pokemonPageItems,
    )
