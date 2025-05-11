package com.example.infludeo.presentation.common.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import com.example.infludeo.R

@Composable
fun LocalImage(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
) {
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
        )
    } else {
        Text(
            text = stringResource(R.string.image_load_failed),
            modifier = modifier,
        )
    }
}
