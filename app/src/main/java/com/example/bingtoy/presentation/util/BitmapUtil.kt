package com.example.bingtoy.presentation.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

fun getBitmapFromFile(filePath: String): Bitmap? =
    File(filePath).takeIf { it.exists() }?.let {
        BitmapFactory.decodeFile(it.absolutePath)
    }
