package com.example.bingtoy.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bingtoy.presentation.navigation.BottomNavTab
import com.example.bingtoy.presentation.navigation.NavScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PokemonAppState(
    val navController: NavHostController,
    val selectedTab: MutableState<BottomNavTab>,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    init {
        navController.currentBackStackEntryFlow
            .onEach { entry ->
                BottomNavTab.entries.firstOrNull { tab ->
                    entry.destination.hierarchy.any { it.route == tab.route }
                }?.let { tab ->
                    if (selectedTab.value != tab) selectedTab.value = tab
                }
            }
            .launchIn(scope)
    }

    val currentScreen: State<NavScreen?>
        @Composable get() {
            val route by currentRoute
            return remember(route) {
                derivedStateOf { NavScreen.Detail.fromRoute(route) }
            }
        }

    private val currentRoute: State<String?>
        @Composable get() {
            val navEntry by navController.currentBackStackEntryAsState()
            return remember(navEntry) {
                derivedStateOf { navEntry?.destination?.route }
            }
        }

    val showBottomBar: Boolean
        @Composable get() {
            val tabs = BottomNavTab.entries.map { it.screen.route }
            return tabs.contains(currentRoute.value)
        }

    fun onTabSelected(newTab: BottomNavTab) {
        if (selectedTab.value == newTab) return
        navController.navigateBottomTab(newTab.route)
    }

    fun moveToBackStack() {
        navController.popBackStack()
    }

    fun moveToDetailScreen(id: Long) {
        navController.navigateSingleTop(NavScreen.Detail.make(id))
    }

    fun moveToFavoriteDetailScreen(id: Long) {
        navController.navigateSingleTop(NavScreen.FavoriteDetail.make(id))
    }

    private fun NavHostController.navigateSingleTop(route: String) = navigate(route) { launchSingleTop = true }

    private fun NavHostController.navigateBottomTab(route: String) {
        if (isInGraph(route)) return
        navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(graph.findStartDestination().id) { saveState = true }
        }
    }

    private fun NavHostController.isInGraph(route: String): Boolean =
        currentBackStackEntry?.destination
            ?.hierarchy?.any { it.route == route } == true
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
