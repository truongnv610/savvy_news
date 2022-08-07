package com.savvy.domain.usecase.news

import com.savvy.data.base.repository.NewsRepository
import com.savvy.data.base.model.news.NewsPagination
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoadListNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    fun execute(
        apiKey: String,
        country: String,
        pageSize: Int,
        page: Int,
        keyword: String,
    ): Single<NewsPagination> {
        return newsRepository.getNewsList(
           apiKey, country, page, pageSize, keyword
        )
    }
}