package com.example.bingtoy.presentation.common.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun showToast(
    @StringRes id: Int,
    context: Context,
) {
    Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
}

fun showToast(
    text: String,
    context: Context,
) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
