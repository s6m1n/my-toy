package com.example.infludeo.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.infludeo.presentation.navigation.BottomNavTab
import com.example.infludeo.presentation.navigation.NavScreen

class PokemonAppState(
    val navController: NavHostController,
    val selectedTab: MutableState<BottomNavTab>,
) {
    val currentScreen: State<NavScreen?>
        @Composable get() {
            val route by currentRoute
            return remember(route) {
                derivedStateOf { NavScreen.Detail.fromRoute(route) }
            }
        }

    val showBottomBar: Boolean
        @Composable get() {
            val tabs = BottomNavTab.entries.map { it.screen.route }
            return tabs.contains(currentRoute.value)
        }

    private val currentRoute: State<String?>
        @Composable get() {
            val navEntry by navController.currentBackStackEntryAsState()
            return remember(navEntry) {
                derivedStateOf { navEntry?.destination?.route }
            }
        }

    fun onTabSelected(newTab: BottomNavTab) {
        selectedTab.value = newTab
        navController.navigate(newTab.route) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun moveToDetailScreen(id: Long) {
        navController.navigate(NavScreen.Detail.make(id)) {
            launchSingleTop = true
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    selectedTab: MutableState<BottomNavTab> = rememberSaveable { mutableStateOf(BottomNavTab.List) },
): PokemonAppState =
    remember(
        navController,
        selectedTab,
    ) {
        PokemonAppState(
            navController = navController,
            selectedTab = selectedTab,
        )
    }
