package com.example.bingtoy.presentation.navigation

import android.content.Intent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.bingtoy.presentation.PokemonAppState
import com.example.bingtoy.presentation.detail.PokemonDetailScreen
import com.example.bingtoy.presentation.detail.PokemonDetailViewModel
import com.example.bingtoy.presentation.favoritedetail.PokemonFavoriteDetailScreen
import com.example.bingtoy.presentation.favoritedetail.PokemonFavoriteScreen
import com.example.bingtoy.presentation.list.PokemonListScreen
import com.example.bingtoy.presentation.lock.LockScreenService
import com.example.bingtoy.presentation.service.ServiceScreenWrapper
import com.example.bingtoy.presentation.socket.SocketScreen

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
        navigation(
            route = BottomNavTab.Socket.route,
            startDestination = NavScreen.Socket.route,
        ) {
            composable(NavScreen.Socket.route) {
                SocketScreen(appState, padding)
            }
        }
        navigation(
            route = BottomNavTab.Service.route,
            startDestination = NavScreen.Service.route,
        ) {
            composable(NavScreen.Service.route) {
                val context = LocalContext.current
                val lockScreenServiceIntent =
                    remember { Intent(context, LockScreenService::class.java) }
                ServiceScreenWrapper(lockScreenServiceIntent)
            }
        }
    }
}

// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    if (!alarmManager.canScheduleExactAlarms()) {
//        // 사용자 설정 화면으로 이동 유도
//        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
//        intent.data = Uri.parse("package:$packageName")
//        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
//        return
//    }
// }
