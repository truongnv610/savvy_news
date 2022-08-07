package com.savvy.core.base.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Keep
data class ErrorResponse(
    @SerializedName("message")
    val message: String = "",

    @SerializedName("error")
    val error: ErrorBody?,
)
