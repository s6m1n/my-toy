package com.example.bingtoy.presentation.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PokemonInfoRow(
    titleText: String,
    valueText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .fillMaxWidth()
                .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
    ) {
        Text(
            text = titleText,
            style =
                TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                ),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(valueText)
    }
}

@Composable
@Preview(showBackground = true)
fun PokemonInfoRowPreview() {
    PokemonInfoRow(
        titleText = "몸무게",
        valueText = "85.5kg",
    )
}
