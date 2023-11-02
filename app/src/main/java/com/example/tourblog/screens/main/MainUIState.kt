package com.example.tourblog.screens.main

import androidx.compose.ui.graphics.ImageBitmap
import com.example.tourblog.data.models.blogs.Price
import com.example.tourblog.utils.PageState

data class MainUIState(
    val buttons: List<Button> = listOf(),
    val categories: List<Category> = listOf(),
    val pageState: PageState = PageState.LOAD
)

data class Button(
    val title: String = "",
    val type: String = ""
)

data class Category(
    val name: String = "",
    val blogList: List<Blog> = listOf(),
    var showedList: List<Blog> = listOf(),
    val template: String = ""
)

data class Blog(
    val id: String = "",
    val title: String = "",
    val subtitle: String? = "",
    var image: ImageBitmap? = null,

    var location: String? = null,
    var price: Price? = null,
    var countTourist: Int? = null
)