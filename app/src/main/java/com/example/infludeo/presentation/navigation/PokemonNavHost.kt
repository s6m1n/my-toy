package com.example.infludeo.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.infludeo.presentation.favorite.PokemonFavoriteScreen
import com.example.infludeo.presentation.list.PokemonListScreen

@Composable
fun PokemonNavHost(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavTab.List.route,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(padding),
    ) {
        composable(
            route = BottomNavTab.List.route,
        ) {
            PokemonListScreen()
        }
        composable(
            route = BottomNavTab.Favorite.route,
        ) {
            PokemonFavoriteScreen()
        }
    }
}
