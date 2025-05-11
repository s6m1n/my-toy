package com.example.infludeo.presentation.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.infludeo.R
import com.example.infludeo.domain.model.InsertResult
import com.example.infludeo.presentation.PokemonAppState
import com.example.infludeo.presentation.common.util.showToast
import com.example.infludeo.presentation.detail.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(
    appState: PokemonAppState,
    viewModel: PokemonDetailViewModel,
) {
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

    val string = stringResource(R.string.unexpected_error)
    LaunchedEffect(Unit) {
        viewModel.errorEvent.collect {
            showToast(string, context)
            appState.moveToBackStack()
        }
    }
}
