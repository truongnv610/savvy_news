package com.savvy.core.base.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Keep
data class ErrorBody(
    @SerializedName("error_code")
    val code: String = "",

    @SerializedName("error_description_en")
    val descriptionEN: String = "",

    @SerializedName("error_description_th")
    val descriptionTH: String = "",
) {
    val message: String?
        get() = when {
            descriptionTH.isNotEmpty() -> descriptionTH
            descriptionEN.isNotEmpty() -> descriptionEN
            else -> null
        }
}
