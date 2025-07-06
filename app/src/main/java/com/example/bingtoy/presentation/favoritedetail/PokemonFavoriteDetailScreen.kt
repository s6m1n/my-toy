package com.example.bingtoy.presentation.favoritedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bingtoy.R
import com.example.bingtoy.domain.model.DeleteResult
import com.example.bingtoy.presentation.common.util.showToast
import com.example.bingtoy.presentation.detail.PokemonDetailUiState
import com.example.bingtoy.presentation.detail.PokemonDetailViewModel
import com.example.bingtoy.presentation.favoritedetail.ui.PokemonFavoriteDetailContent

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
