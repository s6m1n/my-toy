package com.example.infludeo.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.infludeo.presentation.PokemonAppState
import com.example.infludeo.presentation.detail.ui.PokemonDetailScreen
import com.example.infludeo.presentation.detail.viewmodel.PokemonDetailViewModel
import com.example.infludeo.presentation.favorite.ui.PokemonFavoriteScreen
import com.example.infludeo.presentation.favoritedetail.ui.PokemonFavoriteDetailScreen
import com.example.infludeo.presentation.list.ui.PokemonListScreen

@Composable
fun PokemonNavHost(
    appState: PokemonAppState,
    padding: PaddingValues,
) {
    NavHost(
        navController = appState.navController,
        startDestination = BottomNavTab.List.route,
        exitTransition = { ExitTransition.None },
        enterTransition = { EnterTransition.None },
        modifier =
            Modifier
                .fillMaxSize()
                .padding(padding),
    ) {
        navigation(
            route = BottomNavTab.List.route,
            startDestination = NavScreen.List.route,
        ) {
            composable(NavScreen.List.route) {
                PokemonListScreen(appState)
            }
            composable(
                NavScreen.Detail.route,
                arguments = listOf(navArgument(ARGUMENT_POKEMON_ID) { type = NavType.LongType }),
            ) { backStackEntry ->
                val viewModel: PokemonDetailViewModel = hiltViewModel(backStackEntry)
                PokemonDetailScreen(appState, viewModel)
            }
        }
        navigation(
            route = BottomNavTab.Favorite.route,
            startDestination = NavScreen.Favorite.route,
        ) {
            composable(NavScreen.Favorite.route) {
                PokemonFavoriteScreen(appState)
            }
            composable(
                NavScreen.FavoriteDetail.route,
                arguments = listOf(navArgument(ARGUMENT_POKEMON_ID) { type = NavType.LongType }),
            ) { backStackEntry ->
                val viewModel: PokemonDetailViewModel = hiltViewModel(backStackEntry)
                PokemonFavoriteDetailScreen(viewModel)
            }
        }
    }
}
