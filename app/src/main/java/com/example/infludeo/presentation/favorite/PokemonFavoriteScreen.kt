package com.example.infludeo.presentation.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonFavoriteScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Favorite")
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonFavoriteScreenPreview() {
    PokemonFavoriteScreen()
}
