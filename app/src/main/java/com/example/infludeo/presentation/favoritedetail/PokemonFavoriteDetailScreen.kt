package com.example.infludeo.presentation.favoritedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.infludeo.R
import com.example.infludeo.domain.model.DeleteResult
import com.example.infludeo.presentation.common.showToast
import com.example.infludeo.presentation.detail.ui.PokemonDetailUiState
import com.example.infludeo.presentation.detail.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonFavoriteDetailScreen(viewModel: PokemonDetailViewModel) {
    val detailUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    when (detailUiState) {
        is PokemonDetailUiState.Idle -> {}
        is PokemonDetailUiState.Error -> {}
        is PokemonDetailUiState.Success -> {
            PokemonFavoriteDetailContent(
                detailUiState.data,
                { id -> viewModel.deleteFavoritePokemon(id) },
            )
        }
    }
    LaunchedEffect(Unit) {
        viewModel.deleteFavoriteEvent.collect { event ->
            val id =
                when (event) {
                    is DeleteResult.Success -> R.string.pokemon_delete_success
                    DeleteResult.Fail -> R.string.pokemon_delete_fail
                }
            showToast(id, context)
        }
    }
}
