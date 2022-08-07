package com.savvy.core.base.epoxy.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView
import com.savvy.core.base.CyclicAdapter
import com.savvy.domain.SchedulersFacade
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.TimeUnit

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true)
open class OneColumnAutoScrollHorizontalCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Carousel(context, attrs, defStyleAttr) {
    companion object {
        private val SNAP_HELPER_FACTORY = object : SnapHelperFactory() {
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return PagerSnapHelper()
            }
        }
    }

    // Data Members
    private var autoScrollDisposable: Disposable? = null
    private var autoScrollDelay: Long = 3000
    private var shouldAutoScroll: Boolean = false
    private var schedulersFacade: SchedulersFacade? = null

    init {
        setPadding(Padding.dp(0, 0))
        isNestedScrollingEnabled = false
    }

    override fun createLayoutManager(): LayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    override fun getSnapHelperFactory(): SnapHelperFactory {
        return SNAP_HELPER_FACTORY
    }

    override fun swapAdapter(adapter: Adapter<*>?, removeAndRecycleExistingViews: Boolean) {
        super.swapAdapter(adapter, removeAndRecycleExistingViews)
        adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                moveToMiddle(adapter)
                adapter.unregisterAdapterDataObserver(this)
            }
        })
    }

    private fun moveToMiddle(adapter: Adapter<*>?) {
        val firstPosition =
            (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
        if (adapter is CyclicAdapter && firstPosition != null && firstPosition == -1) {
            scrollToActualPosition(0)
        }
    }

    fun scrollToActualPosition(actualPosition: Int) {
        (adapter as? CyclicAdapter)?.apply {
            scrollToPosition(revertPosition(actualPosition))
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                // Pause Animation
                autoScrollDisposable?.dispose()
            }
            MotionEvent.ACTION_UP -> {
                // Resume
                setupAutoScroll(shouldAutoScroll, autoScrollDelay, schedulersFacade)
            }
            else -> Unit
        }

        return super.dispatchTouchEvent(ev)
    }

    fun setupAutoScroll(
        shouldAutoScroll: Boolean = this.shouldAutoScroll,
        delay: Long = autoScrollDelay,
        schedulersFacade: SchedulersFacade?
    ) {
        this.shouldAutoScroll = shouldAutoScroll
        this.autoScrollDelay = delay
        this.schedulersFacade = schedulersFacade

        adapter?.let { recyclerAdapter ->
            autoScrollDisposable?.dispose()

            if (shouldAutoScroll) {
                autoScrollDisposable = Flowable.interval(autoScrollDelay, TimeUnit.MILLISECONDS)
                    .filter {
                        when (recyclerAdapter) {
                            is CyclicAdapter -> recyclerAdapter.actualItemCount > 1
                            else -> false
                        }
                    }
                    .observeOn(schedulersFacade?.ui ?: AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onError = {
                            Timber.e(it)
                        },
                        onNext = {
                            val firstPosition =
                                (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()

                            if (firstPosition != null) {
                                smoothScrollToPosition(firstPosition + 1)
                            }
                        }
                    )
            }
        }
    }
}
