package com.example.infludeo.presentation.service

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
import com.example.infludeo.presentation.PokemonAppState
import com.example.infludeo.presentation.lock.ReceiverService

@Composable
fun ServiceScreen(appState: PokemonAppState) {
    val context = LocalContext.current
    val intent = Intent(context, ReceiverService::class.java)

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            CustomButton(
                text = "잠금화면 ON",
                color = Color.Blue,
                onClick = { },
            )

            CustomButton(
                text = "잠금화면 OFF",
                color = Color.Red,
                onClick = { },
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            CustomButton(
                text = "만보기 ON",
                color = Color.Blue,
                onClick = { },
            )
            CustomButton(
                text = "만보기 OFF",
                color = Color.Red,
                onClick = { },
            )
        }
    }
}
