package com.savvy.app.news.controllers.models

import android.content.Context
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxrelay3.Relay
import com.savvy.app.R
import com.savvy.app.base.extension.displayDDMMM
import com.savvy.app.base.extension.displayDateTime
import com.savvy.app.base.extension.loadUrl
import com.savvy.app.databinding.ListItemNewsBinding
import com.savvy.core.base.epoxy.EpoxyViewBindingModelWithHolder
import com.savvy.data.base.model.news.News
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

@EpoxyModelClass(layout = R.layout.list_item_news)
abstract class NewsGridModel :
    EpoxyViewBindingModelWithHolder<ListItemNewsBinding>() {

    @EpoxyAttribute
    lateinit var news: News

    @EpoxyAttribute
    lateinit var selectedNews: Relay<News>

    override fun shouldSaveViewState() = true

    override fun ListItemNewsBinding.bind(context: Context) {
        with(news) {
            imageNews.loadUrl(context, urlToImage)

            textTitle.text = title
            textContent.text = content
            textTimeUpdated.text = context.getString(R.string.updated_time, publishedAt.displayDateTime())
            cardView.clicks()
                .map {
                   news
                }
                .subscribeBy(
                    onError = Timber::e,
                    onNext = selectedNews::accept
                )
        }
    }
}
