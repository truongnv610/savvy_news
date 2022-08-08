package com.savvy.data.base.model.news

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import org.threeten.bp.ZonedDateTime

@JsonClass(generateAdapter = true)
@Parcelize
data class News(
    val source: SourceData = SourceData(),
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = ""
) :  Parcelable