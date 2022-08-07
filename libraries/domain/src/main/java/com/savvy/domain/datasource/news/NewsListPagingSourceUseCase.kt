package com.savvy.domain.datasource.news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.savvy.data.base.model.news.News
import com.savvy.domain.datasource.news.NewsListPagingSource.Companion.PAGE_SIZE
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Provider

class NewsListPagingSourceUseCase @Inject constructor(
    private val newsListPagingSource: Provider<NewsListPagingSource>
) {
    fun execute(apiKey: String, country: String, keyword: String): Flowable<PagingData<News>> {
        return Pager(PagingConfig(PAGE_SIZE)) {
            newsListPagingSource.get().apply {
                this.apiKey = apiKey
                this.country = country
                this.keyword = keyword
            }
        }.flowable
    }
}