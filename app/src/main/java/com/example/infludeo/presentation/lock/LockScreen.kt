package com.example.infludeo.presentation.lock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.infludeo.presentation.favorite.ui.PokemonFavoriteGridItem
import com.example.infludeo.presentation.list.viewmodel.PokemonListViewModel

@Composable
fun LockScreen(viewModel: PokemonListViewModel = hiltViewModel()) {
    val favoriteUiState = viewModel.favoritePokemonList.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White),
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "잠금 화면",
        )
        favoriteUiState.value.firstOrNull()?.let {
            PokemonFavoriteGridItem(
                item = it,
                modifier = Modifier.fillMaxSize(),
                size = 1000.dp,
            ) { _ -> }
        }
    }
}
