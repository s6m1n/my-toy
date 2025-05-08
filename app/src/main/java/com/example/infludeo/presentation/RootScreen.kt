package com.example.infludeo.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.infludeo.presentation.navigation.BottomNavBar
import com.example.infludeo.presentation.navigation.BottomNavTab
import com.example.infludeo.presentation.navigation.PokemonNavHost

@Composable
fun RootScreen(navController: NavHostController) {
    var selectedTab by rememberSaveable { mutableStateOf(BottomNavTab.List) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentTab = selectedTab,
                onTabSelected = { newTab ->
                    selectedTab = newTab
                    navController.navigate(newTab.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) { padding ->
        PokemonNavHost(navController, padding)
    }
}
