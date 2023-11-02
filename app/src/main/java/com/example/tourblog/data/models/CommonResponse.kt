package com.example.tourblog.data.models

import com.example.tourblog.data.models.main.Error

data class CommonResponse<T>(
    val success: Boolean,
    val error: Error?,
    val time: String,
    val data: T
)
