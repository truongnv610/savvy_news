package com.savvy.domain.datasource.news

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.savvy.data.base.model.news.News
import com.savvy.domain.SchedulersFacade
import com.savvy.domain.usecase.news.LoadListNewsUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NewsListPagingSource @Inject constructor(
    private val loadListNewsUseCase: LoadListNewsUseCase,
    private val schedulersFacade: SchedulersFacade
) : RxPagingSource<Int, News>() {
    companion object {
        const val PAGE_SIZE = 10
    }

    var apiKey: String = ""
    var country: String = "us"
    var keyword: String = ""

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, News>> {
        val page = params.key ?: 1
        return loadListNewsUseCase.execute(
            apiKey,
            country,
            page = page,
            pageSize = PAGE_SIZE,
            keyword = keyword
        ).map<LoadResult<Int, News>> { pagination ->
            LoadResult.Page(
                data = pagination.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (pagination.totalResults > page * PAGE_SIZE) page + 1 else null
            )
        }
            .onErrorReturn { LoadResult.Error(it) }
            .subscribeOn(schedulersFacade.io)
    }
}