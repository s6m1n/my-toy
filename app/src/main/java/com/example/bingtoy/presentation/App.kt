package com.example.bingtoy.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bingtoy.presentation.theme.ToyTheme

@Composable
fun App(appState: PokemonAppState) {
    ToyTheme {
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
            color = MaterialTheme.colorScheme.background,
        ) {
            RootScreen(appState)
        }
    }
}
