package com.savvy.data.base.model.news

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class SourceData(
    val id: String = "",
    val name: String = ""
): Parcelable