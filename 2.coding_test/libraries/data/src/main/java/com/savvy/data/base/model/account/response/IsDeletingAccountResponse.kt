package com.savvy.data.base.model.account.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IsDeletingAccountResponse(
    @Json(name = "isDeletingAccount")
    val isDeletingAccount: Boolean = false,
    @Json(name = "isDeletedOverMonth")
    val isDeletedOverMonth: Boolean = false
)