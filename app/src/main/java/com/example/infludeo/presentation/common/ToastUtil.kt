package com.example.infludeo.presentation.common

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
