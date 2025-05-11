package com.example.infludeo.presentation.detail.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infludeo.R
import com.example.infludeo.domain.model.PokemonDetail
import com.example.infludeo.domain.model.PokemonType
import com.example.infludeo.presentation.common.theme.Green
import com.example.infludeo.presentation.common.ui.VerticalSpacerMedium
import com.example.infludeo.presentation.common.ui.VerticalSpacerSmall

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
private fun FavoriteButton(
    onClicked: () -> Unit,
    color: Color,
    @StringRes id: Int,
) {
    Button(
        onClick = { onClicked() },
        colors =
            ButtonColors(
                containerColor = color,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black,
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text(text = stringResource(id))
        }
    }
}

private fun Int.toHeightString() = "%.1f".format(this * 0.1) + "m"

private fun Int.toWeightString() = "%.1f".format(this * 0.1) + "kg"

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
