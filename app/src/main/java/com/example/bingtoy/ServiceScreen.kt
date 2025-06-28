package com.example.bingtoy.presentation.service

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bingtoy.presentation.PokemonAppState
import com.example.bingtoy.presentation.common.util.showToast
import com.example.bingtoy.presentation.lock.LockScreenService

@Composable
fun ServiceScreen(appState: PokemonAppState) {
    val context = LocalContext.current
    val lockScreenServiceIntent = remember { Intent(context, LockScreenService::class.java) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            CustomButton(
                text = "서비스 ON",
                color = Color.Blue,
                onClick = {
                    context.startForegroundService(lockScreenServiceIntent)
                    showToast("잠금화면을 시작합니다.", context)
                },
            )
            CustomButton(
                text = "서비스 OFF",
                color = Color.Red,
                onClick = {
                    context.stopService(lockScreenServiceIntent)
                    showToast("잠금화면을 종료합니다.", context)
                },
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}
