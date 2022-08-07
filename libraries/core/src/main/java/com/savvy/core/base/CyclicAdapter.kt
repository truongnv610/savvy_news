package com.savvy.core.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyViewHolder
import kotlin.math.abs

/**
 * This Adapter class wraps another Adapter in order to support cyclical scrolling.
 * As the user scrolls, the same list of items in the wrapped adapter will be repeated indefinitely.
 *
 * You may choose to force the RecyclerView to scroll to the middle of the list when it is first created,
 * so that the user can scroll both left and right. Otherwise the list will start at position 0, and it will
 * only be cyclical if the user scrolls right.
 *
 * One issue with this approach is that any adapter changes will only trigger view updates in the initial set
 * of items. It is not recommended to use this if you have to update adapter content. If that is needed, it
 * could potentially be supported by modifying the observerDelegate to also notify the changes at all cyclic
 * intervals.
 */
class CyclicAdapter(private val adapter: RecyclerView.Adapter<EpoxyViewHolder>) :
    RecyclerView.Adapter<EpoxyViewHolder>() {
    companion object {
        @JvmStatic
        private val TAG = CyclicAdapter::class.java.simpleName
    }

    private val observerDelegate: RecyclerView.AdapterDataObserver =
        object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                adapter.notifyDataSetChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                adapter.notifyItemRangeChanged(adjustedPosition(positionStart), itemCount)
            }

            override fun onItemRangeChanged(
                positionStart: Int,
                itemCount: Int,
                payload: Any?
            ) {
                adapter.notifyItemRangeChanged(adjustedPosition(positionStart), itemCount, payload)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                adapter.notifyItemRangeInserted(adjustedPosition(positionStart), itemCount)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                adapter.notifyItemRangeRemoved(adjustedPosition(positionStart), itemCount)
            }

            override fun onItemRangeMoved(
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) { // Unsupported
            }
        }

    init {
        super.setHasStableIds(adapter.hasStableIds())
        super.registerAdapterDataObserver(observerDelegate)
    }

    val actualItemCount: Int
        get() = adapter.itemCount

    /**
     * Do Not Use!!!
     *
     *
     * This returns [Integer.MAX_VALUE] in order to create an infinite looping adapter.
     *
     *
     * Use [.getActualItemCount] instead.
     */
    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    fun adjustedPosition(position: Int): Int {
        return when (actualItemCount) {
            0 -> actualItemCount
            else -> position % actualItemCount
        }
    }

    fun revertPosition(actualPosition: Int): Int {
        return when (actualItemCount) {
            0 -> actualItemCount
            else -> {
                val middlePosition = itemCount / 2
                abs(middlePosition - (middlePosition % actualItemCount)) + actualPosition
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpoxyViewHolder {
        return adapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: EpoxyViewHolder, position: Int) {
        adapter.onBindViewHolder(holder, adjustedPosition(position))
    }

    override fun onBindViewHolder(
        holder: EpoxyViewHolder,
        position: Int,
        payloads: List<*>
    ) {
        adapter.onBindViewHolder(holder, adjustedPosition(position), payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return adapter.getItemViewType(adjustedPosition(position))
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
        adapter.setHasStableIds(hasStableIds)
    }

    override fun getItemId(position: Int): Long {
        return adapter.getItemId(adjustedPosition(position))
    }

    override fun onViewRecycled(holder: EpoxyViewHolder) {
        adapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: EpoxyViewHolder): Boolean {
        return adapter.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: EpoxyViewHolder) {
        adapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: EpoxyViewHolder) {
        adapter.onViewDetachedFromWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        adapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapter.onDetachedFromRecyclerView(recyclerView)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        adapter.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        adapter.unregisterAdapterDataObserver(observer)
    }
}
