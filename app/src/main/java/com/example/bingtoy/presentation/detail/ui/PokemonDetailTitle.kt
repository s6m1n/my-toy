package com.example.bingtoy.presentation.detail.ui

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.bingtoy.R
import com.example.bingtoy.presentation.common.theme.Purple40

@Composable
fun PokemonDetailTitle(
    @StringRes id: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id),
        maxLines = 1,
        style =
            TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Purple40,
            ),
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
fun PokemonDetailTitleTypePreview() {
    PokemonDetailTitle(
        id = R.string.pokemon_type,
    )
}

@Composable
@Preview(showBackground = true)
fun PokemonDetailTitleInformationPreview() {
    PokemonDetailTitle(
        id = R.string.pokemon_information,
    )
}
