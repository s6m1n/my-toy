package com.example.bingtoy.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bingtoy.R
import com.example.bingtoy.presentation.navigation.BottomNavBar
import com.example.bingtoy.presentation.navigation.BottomNavTab
import com.example.bingtoy.presentation.navigation.PokemonNavHost

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
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        appState.currentScreen.value?.let { currentScreen ->
            if (!BottomNavTab.entries.map { it.screen }.contains(currentScreen)) {
                Icon(
                    painter = painterResource(R.drawable.icon_left_arrow),
                    contentDescription = stringResource(R.string.back_arrow),
                    modifier =
                        Modifier
                            .size(36.dp)
                            .padding(start = 16.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { appState.moveToBackStack() },
                            ),
                )
            }
            Text(
                text = stringResource(currentScreen.title),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            )
        }
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
