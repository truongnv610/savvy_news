package com.savvy.app.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.savvy.core.base.BaseViewModel
import com.savvy.data.base.model.news.News
import com.savvy.domain.SchedulersFacade
import com.savvy.domain.datasource.news.NewsListPagingSourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsListPagingSourceUseCase: NewsListPagingSourceUseCase,
    private val schedulersFacade: SchedulersFacade
) : BaseViewModel() {

    val newsPagingDataLiveData by lazy { MutableLiveData<PagingData<News>>() }
    var pagingDisposable: Disposable? = null

    fun loadNewsListData(search: String = "") {
        pagingDisposable?.dispose()
        pagingDisposable = newsListPagingSourceUseCase.execute(apiKey = apiKey, country = "us", keyword = search)
            .cachedIn(viewModelScope)
            .doOnError(Timber::e)
            .subscribeOn(schedulersFacade.io)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {},
                onNext = { pagingData ->
                    newsPagingDataLiveData.value = pagingData
                }
            )
    }
}
