package com.example.infludeo.presentation.service

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomButton(
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
