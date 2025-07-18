package com.example.bingtoy.presentation.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bingtoy.R
import com.example.bingtoy.domain.model.PokemonPageItem
import com.example.bingtoy.presentation.common.CoilImage

@Composable
fun PokemonGridItem(
    item: PokemonPageItem,
    modifier: Modifier = Modifier,
    onItemClicked: (Long) -> Unit,
) {
    Card(
        modifier =
            modifier
                .aspectRatio(1F)
                .clickable { onItemClicked(item.id) },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (item.isFavorite) {
                Icon(
                    painter = painterResource(R.drawable.icon_heart),
                    contentDescription = stringResource(id = R.string.pokemon_add_favorite),
                    tint = Color.Red,
                    modifier =
                        Modifier
                            .size(40.dp)
                            .padding(10.dp)
                            .align(Alignment.TopEnd),
                )
            }
            CoilImage(
                imageUrl = item.imageUrl,
                contentDescription = stringResource(id = R.string.pokemon_image_description),
                modifier =
                    Modifier
                        .size(width = 100.dp, height = 100.dp)
                        .padding(bottom = 24.dp)
                        .align(Alignment.BottomCenter),
            )
            Text(
                text = item.name ?: "",
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
fun PokemonGridItemPreview() {
    PokemonGridItem(
        item = PokemonPageItem(0L, "포켓몬 이름", "imageUrl"),
        modifier = Modifier.size(100.dp),
        onItemClicked = {},
    )
}

@Composable
@Preview
fun PokemonGridItemPreviewLongName() {
    PokemonGridItem(
        item = PokemonPageItem(0L, "엄청 긴 포켓몬 이름", "imageUrl"),
        modifier = Modifier.size(100.dp),
        onItemClicked = {},
    )
}
