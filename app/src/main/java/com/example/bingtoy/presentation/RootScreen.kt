package com.example.bingtoy.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.bingtoy.presentation.navigation.BottomNavBar
import com.example.bingtoy.presentation.navigation.PokemonNavHost
import com.example.bingtoy.presentation.navigation.PokemonTopBar

@Composable
fun RootScreen(appState: PokemonAppState) {
    Scaffold(
        topBar = { PokemonTopBar(appState) },
        bottomBar = {
            if (appState.showBottomBar) {
                BottomNavBar(
                    currentTab = appState.selectedTab.value,
                    onTabSelected = { newTab -> appState.onTabSelected(newTab) },
                )
            }
        },
    ) { padding ->
        PokemonNavHost(appState, padding)
    }
}
