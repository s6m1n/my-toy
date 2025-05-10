package com.example.infludeo.presentation.detail.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.infludeo.presentation.common.VerticalSpacerMedium
import com.example.infludeo.presentation.common.VerticalSpacerSmall
import com.example.infludeo.presentation.ui.theme.Green

@Composable
fun PokemonDetailContent(detailUiModel: PokemonDetail) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(20.dp),
    ) {
        PokemonDetailCard(detailUiModel)
        VerticalSpacerMedium()

        PokemonDetailTitle(
            R.string.pokemon_type,
            Modifier.align(Alignment.Start),
        )
        VerticalSpacerSmall()
        Row {
            for (type in detailUiModel.types) {
                PokemonTypeChip(type)
            }
        }
        VerticalSpacerMedium()

        PokemonDetailTitle(
            R.string.pokemon_information,
            Modifier.align(Alignment.Start),
        )
        VerticalSpacerSmall()
        PokemonInfoRow("키", detailUiModel.height.toHeightString())
        VerticalSpacerSmall()
        PokemonInfoRow("몸무게", detailUiModel.weight.toWeightString())
        VerticalSpacerMedium()

        Button(
            onClick = {},
            colors =
                ButtonColors(
                    containerColor = Green,
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
                Text(text = stringResource(R.string.pokemon_add_favorite))
            }
        }
    }
}

private fun Int.toHeightString() = "%.1f".format(this * 0.1) + "m"

private fun Int.toWeightString() = "%.1f".format(this * 0.1) + "kg"

@Composable
@Preview(showBackground = true)
fun PokemonDetailPreview() {
    PokemonDetailContent(
        PokemonDetail(
            id = 0L,
            name = "piplup",
            height = 4,
            weight = 52,
            types =
                listOf(
                    PokemonType(
                        name = "water",
                        url = "",
                    ),
                    PokemonType(
                        name = "fly",
                        url = "",
                    ),
                ),
            imageUrl = "",
        ),
    )
}
