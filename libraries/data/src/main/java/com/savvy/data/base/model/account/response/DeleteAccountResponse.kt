package com.savvy.data.base.model.account.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteAccountResponse(
    @Json(name = "responseCode")
    val responseCode: Int = 0,
    @Json(name = "success")
    val isSuccess: Boolean = false
)
