package com.savvy.app.news_detail.models

import android.os.Parcelable
import com.savvy.data.base.model.news.News
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsViewState(
    var newsList: List<News> = emptyList()
) : Parcelable
