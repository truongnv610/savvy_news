package com.savvy.app.news_detail

import androidx.lifecycle.SavedStateHandle
import com.savvy.core.base.BaseViewModel
import com.savvy.core.base.extension.toBundle
import com.savvy.data.base.model.news.News
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val saveInstanceStateHandle: SavedStateHandle
) : BaseViewModel() {


    private val newsDetail by lazy {
        FragmentNewsDetailArgs.fromBundle(saveInstanceStateHandle.toBundle).news
    }

    fun loadNewsDetail(): News {
        return newsDetail
    }
}
