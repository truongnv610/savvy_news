package com.savvy.data.base.repository

import com.savvy.data.base.service.NewsService
import com.savvy.data.base.model.news.NewsPagination
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsService: NewsService
) {

    fun getNewsList(
        apiKey: String,
        country: String,
        page: Int,
        pageSize: Int,
        keyword: String
    ): Single<NewsPagination> {
        return newsService.getListNews(
            apiKey, country, page, pageSize, keyword
        )
    }
}