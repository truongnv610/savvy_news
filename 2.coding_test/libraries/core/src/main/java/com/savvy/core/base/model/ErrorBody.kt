package com.savvy.core.base.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Keep
data class ErrorBody(
    @SerializedName("code")
    val code: String = "",

    @SerializedName("message")
    val message: String = "",
)
