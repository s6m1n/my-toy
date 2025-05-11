package com.example.infludeo.presentation.favoritedetail.ui

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
import com.example.infludeo.presentation.common.ui.VerticalSpacerMedium
import com.example.infludeo.presentation.common.ui.VerticalSpacerSmall
import com.example.infludeo.presentation.detail.ui.PokemonDetailTitle
import com.example.infludeo.presentation.detail.ui.PokemonInfoRow
import com.example.infludeo.presentation.detail.ui.PokemonTypeChip
import com.example.infludeo.presentation.detail.ui.dummyPokemonDetail

@Composable
fun PokemonFavoriteDetailContent(
    pokemonDetail: PokemonDetail,
    onDeleteFavoritePokemon: (Long) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
    ) {
        PokemonFavoriteDetailCard(pokemonDetail)
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
            { onDeleteFavoritePokemon(pokemonDetail.id) },
            Color.Red,
            R.string.pokemon_delete_favorite,
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
fun PokemonFavoriteDetailPreview() {
    PokemonFavoriteDetailContent(
        dummyPokemonDetail,
        {},
    )
}
