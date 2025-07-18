package com.example.bingtoy.presentation.navigation

import androidx.annotation.StringRes
import com.example.bingtoy.R

const val ARGUMENT_POKEMON_ID = "id"

sealed class NavScreen(
    val route: String,
    @StringRes val title: Int,
) {
    data object Service : NavScreen(
        route = "service",
        title = R.string.top_bar_title_service,
    )

    data object Socket : NavScreen(
        route = "socket",
        title = R.string.top_bar_title_socket,
    )

    data object List : NavScreen(
        route = "pokemon_list",
        title = R.string.top_bar_title_list,
    )

    data object Favorite : NavScreen(
        route = "pokemon_favorite",
        title = R.string.top_bar_title_favorite,
    )

    data object Detail : NavScreen(
        route = "pokemon_detail/{$ARGUMENT_POKEMON_ID}",
        title = R.string.top_bar_title_detail,
    ) {
        fun make(id: Long) = "pokemon_detail/$id"
    }

    data object FavoriteDetail : NavScreen(
        route = "pokemon_favorite_detail/{$ARGUMENT_POKEMON_ID}",
        title = R.string.top_bar_title_favorite_detail,
    ) {
        fun make(id: Long) = "pokemon_favorite_detail/$id"
    }

    fun fromRoute(route: String?): NavScreen? {
        if (route == null) return null
        return when (route) {
            List.route -> List
            Favorite.route -> Favorite
            Detail.route -> Detail
            FavoriteDetail.route -> FavoriteDetail
            Socket.route -> Socket
            Service.route -> Service
            else -> null
        }
    }
}
