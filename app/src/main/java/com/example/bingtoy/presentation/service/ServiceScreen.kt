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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bingtoy.presentation.common.util.showToast

@Composable
fun ServiceScreenWrapper(lockScreenServiceIntent: Intent) {
    val context = LocalContext.current
    ServiceScreen(
        onLockScreenStart = {
            context.startForegroundService(lockScreenServiceIntent)
            showToast("잠금화면을 시작합니다.", context)
        },
        onLockScreenStop = {
            context.stopService(lockScreenServiceIntent)
            showToast("잠금화면을 종료합니다.", context)
        },
    )
}

@Composable
private fun ServiceScreen(
    onLockScreenStart: () -> Unit,
    onLockScreenStop: () -> Unit,
) {
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
                onClick = onLockScreenStart,
            )
            CustomButton(
                text = "서비스 OFF",
                color = Color.Red,
                onClick = onLockScreenStop,
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}
