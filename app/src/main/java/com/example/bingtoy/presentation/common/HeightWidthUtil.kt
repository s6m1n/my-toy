package com.example.bingtoy.presentation.common

fun Int.toHeightString() = "%.1f".format(this * 0.1) + "m"

fun Int.toWeightString() = "%.1f".format(this * 0.1) + "kg"
