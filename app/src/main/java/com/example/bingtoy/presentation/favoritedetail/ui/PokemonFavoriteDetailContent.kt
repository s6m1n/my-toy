package com.example.bingtoy.presentation.favoritedetail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bingtoy.R
import com.example.bingtoy.domain.model.PokemonDetail
import com.example.bingtoy.presentation.common.FavoriteButton
import com.example.bingtoy.presentation.common.VerticalSpacerMedium
import com.example.bingtoy.presentation.common.VerticalSpacerSmall
import com.example.bingtoy.presentation.common.toHeightString
import com.example.bingtoy.presentation.common.toWeightString
import com.example.bingtoy.presentation.detail.ui.PokemonDetailTitle
import com.example.bingtoy.presentation.detail.ui.PokemonInfoRow
import com.example.bingtoy.presentation.detail.ui.PokemonTypeChip
import com.example.bingtoy.presentation.detail.ui.dummyPokemonDetail

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
@Preview(showBackground = true)
fun PokemonFavoriteDetailPreview() {
    PokemonFavoriteDetailContent(
        dummyPokemonDetail,
        {},
    )
}
