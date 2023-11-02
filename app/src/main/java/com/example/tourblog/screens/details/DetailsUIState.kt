package com.example.tourblog.screens.details

import androidx.compose.ui.graphics.ImageBitmap
import com.example.tourblog.utils.PageState

data class DetailsUIState(
    val date: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val content: String? = null,
    val image: ImageBitmap? = null,
    val pageState: PageState = PageState.LOAD,
)