package com.example.tourblog.data.models.details

import com.example.tourblog.data.models.blogs.Images

data class DetailsData(
    val date: String,
    val image: Images,
    val title: String,
    val subtitle: String,
    val content: String
)