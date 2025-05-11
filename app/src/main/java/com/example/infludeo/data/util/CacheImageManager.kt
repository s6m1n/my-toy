package com.example.infludeo.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class CacheImageManager
    @Inject
    constructor() {
        suspend fun downloadImageFromUrl(
            context: Context,
            imageUrl: String,
            filename: String,
        ): File? {
            val request =
                ImageRequest.Builder(context)
                    .data(imageUrl)
                    .allowHardware(false)
                    .build()

            val result = context.imageLoader.execute(request)
            val drawable = result.drawable ?: return null
            val bitmap = (drawable as BitmapDrawable).bitmap

            val file = File(context.cacheDir, filename)
            withContext(Dispatchers.IO) {
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
            }
            return file
        }
    }
