package com.example.bingtoy.presentation.detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bingtoy.R
import com.example.bingtoy.domain.model.PokemonDetail
import com.example.bingtoy.domain.model.PokemonType
import com.example.bingtoy.presentation.common.FavoriteButton
import com.example.bingtoy.presentation.common.VerticalSpacerMedium
import com.example.bingtoy.presentation.common.VerticalSpacerSmall
import com.example.bingtoy.presentation.common.theme.Green
import com.example.bingtoy.presentation.common.toHeightString
import com.example.bingtoy.presentation.common.toWeightString

@Composable
fun PokemonDetailContent(
    pokemonDetail: PokemonDetail,
    onAddFavoritePokemon: (Long) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
    ) {
        PokemonDetailCard(pokemonDetail)
        VerticalSpacerMedium()

        PokemonDetailTitle(
            R.string.pokemon_type,
            Modifier.align(Alignment.Start),
        )
        VerticalSpacerSmall()
        Row {
            for (type in pokemonDetail.types) {
                PokemonTypeChip(type)
            }
        }
        VerticalSpacerMedium()

        PokemonDetailTitle(
            R.string.pokemon_information,
            Modifier.align(Alignment.Start),
        )
        VerticalSpacerSmall()
        PokemonInfoRow("키", pokemonDetail.height.toHeightString())
        VerticalSpacerSmall()
        PokemonInfoRow("몸무게", pokemonDetail.weight.toWeightString())
        VerticalSpacerMedium()

        FavoriteButton(
            { onAddFavoritePokemon(pokemonDetail.id) },
            Green,
            R.string.pokemon_add_favorite,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PokemonDetailPreview() {
    PokemonDetailContent(
        dummyPokemonDetail,
        {},
    )
}

internal val dummyPokemonDetail =
    PokemonDetail(
        id = 0L,
        name = "piplup",
        height = 4,
        weight = 52,
        types =
            listOf(
                PokemonType(
                    name = "water",
                    url = "https://pokeapi.co/api/v2/type/11/",
                ),
            ),
        imageUrl = "",
    )
