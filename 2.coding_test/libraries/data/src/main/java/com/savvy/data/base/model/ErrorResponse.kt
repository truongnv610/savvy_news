package com.savvy.data.base.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val statusCode: Int,
    val error: String,
    val message: String,
    val code: String
)
