package com.example.infludeo.presentation.favoritedetail.ui

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infludeo.domain.model.PokemonDetail
import com.example.infludeo.presentation.common.ui.LocalImage
import com.example.infludeo.presentation.common.util.getBitmapFromFile
import com.example.infludeo.presentation.detail.ui.dummyPokemonDetail

@Composable
fun PokemonFavoriteDetailCard(pokemonDetail: PokemonDetail) {
    Card(
        modifier =
            Modifier
                .aspectRatio(1F),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            pokemonDetail.imagePath?.let {
                LocalImage(
                    bitmap = getBitmapFromFile(it),
                    modifier =
                        Modifier
                            .size(500.dp)
                            .padding(bottom = 50.dp)
                            .align(Alignment.BottomCenter),
                )
            }
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
fun PokemonFavoriteDetailCardPreview() {
    PokemonFavoriteDetailCard(dummyPokemonDetail)
}
