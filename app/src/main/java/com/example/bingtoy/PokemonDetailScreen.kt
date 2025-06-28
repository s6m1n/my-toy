package com.example.bingtoy.presentation.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bingtoy.R
import com.example.bingtoy.domain.model.InsertResult
import com.example.bingtoy.presentation.PokemonAppState
import com.example.bingtoy.presentation.common.util.showToast
import com.example.bingtoy.presentation.detail.viewmodel.PokemonDetailViewModel

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
