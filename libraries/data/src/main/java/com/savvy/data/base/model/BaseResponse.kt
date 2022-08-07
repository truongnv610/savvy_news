package com.savvy.data.base.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class BaseResponse(
    @Json(name = "status") val status: Boolean = false,
    @Json(name = "message") val message: String = ""
): Parcelable
