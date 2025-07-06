package com.example.bingtoy.presentation.lock

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bingtoy.R
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SwipeLockScreen(onSwipe: () -> Unit) {
    val offsetX = remember { mutableStateOf(0f) }
    LockScreen(offsetX, onSwipe)
}

@Composable
fun LockScreen(
    offsetX: MutableState<Float>,
    onSwipe: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .offset { IntOffset(offsetX.value.toInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            // 스와이프가 끝났을 때 특정 거리 이상이면 종료
                            if (offsetX.value > with(density) { 200.dp.toPx() }) {
                                onSwipe()
                            } else {
                                // 원래 위치로 되돌리기
                                offsetX.value = 0f
                            }
                        },
                    ) { change, dragAmount ->
                        change.consume()
                        offsetX.value += dragAmount
                    }
                }
                .fillMaxSize()
                .background(Color.White),
    ) {
        Spacer(modifier = Modifier.height(250.dp))
        LockScreenClock(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(R.mipmap.ic_launcher),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.5F),
        )
    }
}

@Composable
fun LockScreenClock(modifier: Modifier) {
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    // 1초마다 시간 갱신
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000)
        }
    }

    Text(
        text = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
        fontSize = 40.sp,
        color = Color.Gray,
        modifier = modifier,
    )
}
