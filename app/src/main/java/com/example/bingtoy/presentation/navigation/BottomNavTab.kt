package com.example.bingtoy.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.bingtoy.R

enum class BottomNavTab(
    val route: String,
    val screen: NavScreen,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
) {
    List(
        route = "pokemon_list_graph",
        screen = NavScreen.List,
        label = R.string.bottom_nav_label_list,
        icon = R.drawable.icon_list,
    ),
    Favorite(
        route = "pokemon_favorite_graph",
        screen = NavScreen.Favorite,
        label = R.string.bottom_nav_label_favorite,
        icon = R.drawable.icon_heart,
    ),
    Socket(
        route = "socket_graph",
        screen = NavScreen.Socket,
        label = R.string.bottom_nav_label_socket,
        icon = R.drawable.icon_chart,
    ),
    Service(
        route = "service_graph",
        screen = NavScreen.Service,
        label = R.string.bottom_nav_label_service,
        icon = R.drawable.icon_tools,
    ),
}
