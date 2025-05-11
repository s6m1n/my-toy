package com.example.infludeo.presentation.detail.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.infludeo.R
import com.example.infludeo.domain.model.InsertResult
import com.example.infludeo.presentation.common.showToast
import com.example.infludeo.presentation.detail.ui.PokemonDetailUiState
import com.example.infludeo.presentation.detail.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(viewModel: PokemonDetailViewModel) {
    val detailUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    when (detailUiState) {
        is PokemonDetailUiState.Idle -> {}
        is PokemonDetailUiState.Error -> {}
        is PokemonDetailUiState.Success -> {
            PokemonDetailContent(
                detailUiState.data,
                { viewModel.addFavoritePokemon(context) },
            )
        }
    }
    LaunchedEffect(Unit) {
        viewModel.insertFavoriteEvent.collect { event ->
            val id =
                when (event) {
                    is InsertResult.Success -> R.string.pokemon_insert_success
                    InsertResult.LimitReached -> R.string.pokemon_limit_reached
                    InsertResult.Duplicated -> R.string.pokemon_duplicated
                }
            showToast(id, context)
        }
    }
}
