package com.example.tourblog.navigation

import com.example.tourblog.R
import com.example.tourblog.utils.Constants.CHATS_ROUTE
import com.example.tourblog.utils.Constants.MAIN_ROUTE
import com.example.tourblog.utils.Constants.MAPS_ROUTE
import com.example.tourblog.utils.Constants.PROFILE_ROUTE
import com.example.tourblog.utils.Constants.RESERVATION_ROUTE

sealed class BottomNavBar(
    val route: String,
    val title: Int,
    val icon: Int,
) {
    object Main : BottomNavBar(
        route = MAIN_ROUTE,
        title = R.string.main_title,
        icon = R.drawable.ic_main
    )

    object Map : BottomNavBar(
        route = MAPS_ROUTE,
        title = R.string.maps_title,
        icon = R.drawable.ic_maps
    )

    object Reservation : BottomNavBar(
        route = RESERVATION_ROUTE,
        title = R.string.reservation_title,
        icon = R.drawable.ic_reservation
    )

    object Chats : BottomNavBar(
        route = CHATS_ROUTE,
        title = R.string.chats_title,
        icon = R.drawable.ic_chats
    )

    object Profile : BottomNavBar(
        route = PROFILE_ROUTE,
        title = R.string.profile_title,
        icon = R.drawable.ic_profile
    )
}
