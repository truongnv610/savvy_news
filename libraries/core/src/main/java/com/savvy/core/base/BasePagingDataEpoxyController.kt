package com.savvy.core.base

import android.content.Context
import android.os.Handler
import androidx.paging.LoadState
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.savvy.core.base.epoxy.view.epoxyLazyLoadingItemView
import com.savvy.data.base.di.ASYNC_BACKGROUND_THREAD_HANDLER
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.ObsoleteCoroutinesApi
import javax.inject.Named

@ObsoleteCoroutinesApi
abstract class BasePagingDataEpoxyController<T : Any>(
    @ActivityContext private val context: Context,
    @Named(ASYNC_BACKGROUND_THREAD_HANDLER) handler: Handler
) : PagingDataEpoxyController<T>(
    modelBuildingHandler = handler,
    diffingHandler = handler
) {
    companion object {
        @JvmStatic
        private val TAG = BasePagingDataEpoxyController::class.java.simpleName
    }

    init {
        addLoadStateListener {
            isLoading = it.refresh is LoadState.Loading
            isError = it.refresh is LoadState.Error
            isLoadingItem = it.append is LoadState.Loading
        }
    }

    var isLoading: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                requestModelBuild()
            }
        }
    var isError: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                requestModelBuild()
            }
        }
    var isLoadingItem: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                requestModelBuild()
            }
        }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty() && isLoading) epoxyLazyLoadingItemView {
            id("placeholder")
            spanSizeOverride { totalSpanCount, _, _ ->
                totalSpanCount
            }
        }
        super.addModels(models)
    }
}