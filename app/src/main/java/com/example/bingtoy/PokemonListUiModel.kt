package com.example.bingtoy.presentation.list

import com.example.bingtoy.domain.model.PokemonPage
import com.example.bingtoy.domain.model.PokemonPageItem

data class PokemonListUiModel(
    val nextOffset: Int?,
    val pokemons: List<PokemonPageItem>,
) {
    val hasNextPage: Boolean get() = nextOffset != null

    fun mergeWith(newPokemonListUiModel: PokemonListUiModel): PokemonListUiModel {
        return PokemonListUiModel(
            nextOffset = newPokemonListUiModel.nextOffset,
            pokemons = pokemons + newPokemonListUiModel.pokemons,
        )
    }

    companion object {
        val emptyPokemonListUiModel = PokemonListUiModel(0, emptyList())
    }
}

fun PokemonPage.toUiModel() =
    PokemonListUiModel(
        nextOffset = nextOffset,
        pokemons = pokemonPageItems,
    )
