package com.example.infludeo.presentation.detail.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infludeo.domain.model.PokemonType

@Composable
fun PokemonTypeChip(type: PokemonType) {
    Text(
        type.name,
        modifier =
            Modifier
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape,
                )
                .padding(horizontal = 10.dp, vertical = 5.dp),
        style = TextStyle(color = Color.Gray),
    )
}

@Composable
@Preview
fun PokemonTypeChipPreview() {
    PokemonTypeChip(
        PokemonType(
            name = "비행",
            url = "",
        ),
    )
}
