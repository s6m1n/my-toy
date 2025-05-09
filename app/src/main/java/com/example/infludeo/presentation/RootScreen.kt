package com.example.infludeo.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infludeo.presentation.navigation.BottomNavBar
import com.example.infludeo.presentation.navigation.PokemonNavHost

@Composable
fun RootScreen(appState: PokemonAppState) {
    Scaffold(
        topBar = { TopBar(appState) },
        bottomBar = { BottomNavBar(appState) },
    ) { padding ->
        PokemonNavHost(appState, padding)
    }
}

@Composable
private fun TopBar(appState: PokemonAppState) {
    appState.currentScreen.value?.title?.let {
        Text(
            text = stringResource(it),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
        )
    }
}

@Composable
private fun BottomNavBar(appState: PokemonAppState) {
    if (appState.showBottomBar) {
        BottomNavBar(
            currentTab = appState.selectedTab.value,
            onTabSelected = { newTab -> appState.onTabSelected(newTab) },
        )
    }
}
