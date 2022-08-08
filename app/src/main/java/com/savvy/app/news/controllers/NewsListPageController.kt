package com.savvy.app.news.controllers

import android.content.Context
import android.os.Handler
import com.airbnb.epoxy.EpoxyModel
import com.jakewharton.rxrelay3.PublishRelay
import com.savvy.app.news.controllers.models.NewsGridModel_
import com.savvy.app.news.controllers.models.viewEmptyItem
import com.savvy.core.base.BasePagingDataEpoxyController
import com.savvy.core.base.epoxy.view.EpoxyLoadingItemViewModel_
import com.savvy.core.base.epoxy.view.epoxyLazyLoadingItemView
import com.savvy.data.base.di.ASYNC_BACKGROUND_THREAD_HANDLER
import com.savvy.data.base.model.news.News
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import javax.inject.Named

class NewsListPageController @Inject constructor(
    @ActivityContext private val context: Context,
    @Named(ASYNC_BACKGROUND_THREAD_HANDLER) handler: Handler) : BasePagingDataEpoxyController<News>(context, handler) {

    private val selectedNews by lazy { PublishRelay.create<News>() }

    override fun buildItemModel(currentPosition: Int, item: News?): EpoxyModel<*> {
        return when (item) {
            null -> EpoxyLoadingItemViewModel_()
                .id(-currentPosition)
            else -> NewsGridModel_()
                .id(currentPosition.toString())
                .news(item)
                .selectedNews(this.selectedNews)
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (isLoadingItem) {
            epoxyLazyLoadingItemView {
                id("PROMOTION_LIST_LOADING")
                spanSizeOverride { totalSpanCount, _, _ ->
                    totalSpanCount
                }
            }
        }
        if (models.isEmpty() && (!isLoading || isError)) {
            viewEmptyItem{
                id("NO_DATA")
            }
        }
        super.addModels(models)
    }

    fun bindSelectedNews() = selectedNews.hide()
}
