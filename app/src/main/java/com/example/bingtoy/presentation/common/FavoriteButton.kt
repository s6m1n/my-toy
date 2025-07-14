package com.example.bingtoy.presentation.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun FavoriteButton(
    onClicked: () -> Unit,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    @StringRes id: Int,
) {
    Button(
        onClick = { onClicked() },
        colors =
            ButtonColors(
                containerColor = color,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                disabledContentColor = MaterialTheme.colorScheme.onSurface,
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text(text = stringResource(id))
        }
    }
}
