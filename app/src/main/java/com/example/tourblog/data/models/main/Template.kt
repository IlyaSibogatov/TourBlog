package com.example.tourblog.data.models.main

import com.google.gson.annotations.SerializedName

data class Template(
    val card: String,
    @SerializedName("object") val obj: String,
    val size: String,
    val direction: String,
    val url: String
)
