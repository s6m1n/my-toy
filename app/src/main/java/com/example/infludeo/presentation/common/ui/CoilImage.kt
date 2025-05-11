package com.example.infludeo.presentation.common.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.infludeo.R

@Composable
fun CoilImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    placeHolder: Drawable? = null,
) {
    val painter =
        rememberAsyncImagePainter(
            model =
                ImageRequest.Builder(context = LocalContext.current)
                    .data(imageUrl)
                    .placeholder(placeHolder)
                    .size(100)
                    .build(),
        )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
    )

    val state = painter.state

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (state) {
            is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
            is AsyncImagePainter.State.Error -> Text(stringResource(R.string.image_load_failed))
            else -> Unit
        }
    }
}
