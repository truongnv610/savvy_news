package com.savvy.data.base.model.news

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class NewsPagination(
    @Json(name = "totalResults")
    var totalResults: Int = 0,

    @Json(name = "articles")
    var articles: List<News> = emptyList(),

    @Json(name = "status")
    var status: String = "",
) : Parcelable
