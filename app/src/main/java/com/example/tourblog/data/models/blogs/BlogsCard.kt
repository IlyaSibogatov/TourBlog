package com.example.tourblog.data.models.blogs

import com.google.gson.annotations.SerializedName

data class BlogsCard(
    val id: String,
    val image: Images,
    val title: String,
    val subtitle: String?,

    @SerializedName("countTourist") val countTourist: Int,
    @SerializedName("price") val price: Price,
    @SerializedName("location") val location: String
)

data class Images(
    val sm: String,
    val md: String,
    val lg: String
)

data class Price(
    val factPrice: Int,
    val price: Int,
    val currency: String,
    val typePrice: String,
)
