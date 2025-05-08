package com.example.infludeo.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.infludeo.R

enum class BottomNavTab(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
) {
    List("pokemon_main", R.string.list, R.drawable.icon_list),
    Favorite("pokemon_favorite", R.string.favorite, R.drawable.icon_heart),
}
