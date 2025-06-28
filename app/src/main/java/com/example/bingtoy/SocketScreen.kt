package com.example.bingtoy.presentation.socket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bingtoy.presentation.PokemonAppState

@Composable
fun SocketScreen(
    appState: PokemonAppState,
    bottomPadding: PaddingValues,
    viewModel: SocketViewModel = hiltViewModel(),
) {
    val messages by viewModel.messages.collectAsState()
    val text = rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier =
                Modifier
                    .height(100.dp)
                    .background(color = Color.Yellow),
        )
        ChattingLazyColumn(
            messages,
            Modifier.weight(1F),
            listState,
        )
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions =
                KeyboardActions(onDone = {
                    viewModel.send(text.value)
                    text.value = ""
                }),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .imePadding(),
        )
    }
}

@Composable
private fun CustomButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        colors =
            ButtonColors(
                containerColor = color,
                contentColor = Color.White,
                disabledContainerColor = color,
                disabledContentColor = Color.White,
            ),
    ) {
        Text(text = text)
    }
}
