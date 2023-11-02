package com.example.tourblog.utils

import com.example.tourblog.R

enum class PageState {
    LOAD, READY
}

enum class Templates(val value: String) {
    BLOG("blog")
}

enum class PriceType(val value: String) {
    NIGHT("night"),
    ALL("all")
}

enum class ButtonType(
    val value: String,
    val colors: List<Int>,
    val icon: Int
) {
    MAP_MARKER(
        value = "g-11",
        colors = listOf(
            R.color.g111,
            R.color.g112
        ),
        icon = R.drawable.ic_map_marker_path
    ),
    HELP(
        value = "g-12",
        colors = listOf(
            R.color.g121,
            R.color.g122
        ),
        icon = R.drawable.ic_help_circle_outline
    ),
    WEATHER(
        value = "g-13",
        colors = listOf(
            R.color.g131,
            R.color.g132
        ),
        icon = R.drawable.ic_weather_sunny
    ),
    THREE_D_VIEW(
        value = "g-19",
        colors = listOf(
            R.color.g191,
            R.color.g192
        ),
        icon = R.drawable.ic_3d_view
    )
}
