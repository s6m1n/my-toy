package com.example.bingtoy.presentation.favoritedetail.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bingtoy.domain.model.PokemonDetail
import com.example.bingtoy.presentation.common.LocalImage
import com.example.bingtoy.presentation.common.util.getBitmapFromFile
import com.example.bingtoy.presentation.detail.ui.dummyPokemonDetail

@Composable
fun PokemonFavoriteGridItem(
    item: PokemonDetail,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    onItemClicked: (Long) -> Unit,
) {
    Card(
        modifier =
            modifier
                .aspectRatio(1F)
                .clickable { onItemClicked(item.id) },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            item.imagePath?.let { path ->
                LocalImage(
                    bitmap = getBitmapFromFile(path),
                    modifier =
                        Modifier
                            .size(width = size, height = size)
                            .padding(bottom = 24.dp)
                            .align(Alignment.BottomCenter),
                )
            }
            Text(
                text = item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier =
                    Modifier
                        .padding(bottom = 6.dp)
                        .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
@Preview
fun PokemonFavoriteGridItemPreview() {
    PokemonFavoriteGridItem(
        item = dummyPokemonDetail,
        modifier = Modifier.size(100.dp),
        onItemClicked = {},
    )
}
