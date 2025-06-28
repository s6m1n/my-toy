package com.example.bingtoy.presentation.detail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bingtoy.R
import com.example.bingtoy.domain.model.PokemonDetail
import com.example.bingtoy.presentation.common.ui.CoilImage

@Composable
fun PokemonDetailCard(pokemonDetail: PokemonDetail) {
    Card(
        modifier =
            Modifier
                .aspectRatio(1F),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CoilImage(
                imageUrl = pokemonDetail.imageUrl,
                contentDescription = stringResource(id = R.string.pokemon_image_description),
                modifier =
                    Modifier
                        .size(500.dp)
                        .padding(bottom = 50.dp)
                        .align(Alignment.BottomCenter),
            )
            Text(
                text = pokemonDetail.name,
                maxLines = 1,
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 20.sp),
                modifier =
                    Modifier
                        .padding(bottom = 30.dp)
                        .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PokemonDetailCardPreview() {
    PokemonDetailCard(dummyPokemonDetail)
}
