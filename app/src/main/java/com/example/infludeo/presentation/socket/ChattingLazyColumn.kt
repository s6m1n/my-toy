package com.example.infludeo.presentation.socket

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChattingLazyColumn(
    messages: List<String>,
    modifier: Modifier,
    listState: LazyListState,
) {
    LazyColumn(
        state = listState,
        modifier = modifier,
    ) {
        items(messages) { message ->
            Text(
                text = message,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
            )
        }
    }
}
