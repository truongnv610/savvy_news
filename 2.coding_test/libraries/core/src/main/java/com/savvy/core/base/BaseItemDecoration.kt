package com.savvy.core.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.*

/**
 * Created by CThawanapong on 23/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
open class BaseItemDecoration  : RecyclerView.ItemDecoration() {
    companion object {
        @JvmStatic
         private val VERTICAL = OrientationHelper.VERTICAL
    }

     private var orientation = -1
     private var spanCount = -1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (orientation == -1) {
            orientation = getOrientation(parent)
        }

        if (spanCount == -1) {
            spanCount = getTotalSpan(parent)
        }

        val childCount = parent.layoutManager?.itemCount ?: 0
        val childIndex = parent.getChildAdapterPosition(view)
        val childViewHolder = parent.getChildViewHolder(view)

        val itemSpanSize = getItemSpanSize(parent, childIndex)
        val spanIndex = getItemSpanIndex(parent, childIndex)

        /* INVALID SPAN */
        if (spanCount < 1) return

        setSpacings(outRect, parent, childCount, childIndex, childViewHolder, itemSpanSize, spanIndex)
    }

    open fun setSpacings(
        outRect: Rect,
        parent: RecyclerView,
        childCount: Int,
        childIndex: Int,
        childViewHolder: RecyclerView.ViewHolder,
        itemSpanSize: Int,
        spanIndex: Int
    ) {
    }

    fun getTotalSpan(parent: RecyclerView): Int {
        return with(parent.layoutManager) {
            when (this) {
                is GridLayoutManager -> this.spanCount
                is StaggeredGridLayoutManager -> this.spanCount
                is LinearLayoutManager -> 1
                else -> -1
            }
        }
    }

    fun getItemSpanSize(parent: RecyclerView, childIndex: Int): Int {
        return with(parent.layoutManager) {
            when (this) {
                is GridLayoutManager -> this.spanSizeLookup.getSpanSize(childIndex)
                is StaggeredGridLayoutManager -> 1
                is LinearLayoutManager -> 1
                else -> -1
            }
        }
    }

     fun getItemSpanIndex(parent: RecyclerView, childIndex: Int): Int {
        return with(parent.layoutManager) {
            when (this) {
                is GridLayoutManager -> this.spanSizeLookup.getSpanIndex(childIndex, spanCount)
                is StaggeredGridLayoutManager -> childIndex % spanCount
                is LinearLayoutManager -> 0
                else -> -1
            }
        }
    }

     fun getOrientation(parent: RecyclerView): Int {
        return with(parent.layoutManager) {
            when (this) {
                is GridLayoutManager -> this.orientation
                is StaggeredGridLayoutManager -> this.orientation
                is LinearLayoutManager -> this.orientation
                else -> VERTICAL
            }
        }
    }

     fun isLeftEdge(
        parent: RecyclerView,
        childCount: Int,
        childIndex: Int,
        itemSpanSize: Int,
        spanIndex: Int
    ): Boolean {
        if (orientation == VERTICAL) {
            return spanIndex == 0
        } else {
            return childIndex == 0 || isFirstItemEdgeValid(childIndex < spanCount, parent, childIndex)
        }
    }

     fun isRightEdge(
        parent: RecyclerView,
        childCount: Int,
        childIndex: Int,
        itemSpanSize: Int,
        spanIndex: Int
    ): Boolean {
        if (orientation == VERTICAL) {
            return spanIndex + itemSpanSize == spanCount
        } else {
            return isLastItemEdgeValid(childIndex >= childCount - spanCount, parent, childCount, childIndex, spanIndex)
        }
    }

     fun isTopEdge(
        parent: RecyclerView,
        childCount: Int,
        childIndex: Int,
        itemSpanSize: Int,
        spanIndex: Int
    ): Boolean {
        if (orientation == VERTICAL) {
            return childIndex == 0 || isFirstItemEdgeValid(childIndex < spanCount, parent, childIndex)
        } else {
            return spanIndex == 0
        }
    }

     fun isBottomEdge(
        parent: RecyclerView,
        childCount: Int,
        childIndex: Int,
        itemSpanSize: Int,
        spanIndex: Int
    ): Boolean {
        if (orientation == VERTICAL) {
            return isLastItemEdgeValid(childIndex >= childCount - spanCount, parent, childCount, childIndex, spanIndex)
        } else {
            return spanIndex + itemSpanSize == spanCount
        }
    }

     fun isFirstItemEdgeValid(
        isOneOfFirstItems: Boolean,
        parent: RecyclerView,
        childIndex: Int
    ): Boolean {
        var totalSpanArea = 0
        if (isOneOfFirstItems) {
            for (i in childIndex downTo 0) {
                totalSpanArea += getItemSpanSize(parent, i)
            }
        }

        return isOneOfFirstItems && totalSpanArea <= spanCount
    }

     fun isLastItemEdgeValid(
        isOneOfLastItems: Boolean,
        parent: RecyclerView,
        childCount: Int,
        childIndex: Int,
        spanIndex: Int
    ): Boolean {
        var totalSpanRemaining = 0
        if (isOneOfLastItems) {
            for (i in childIndex until childCount) {
                totalSpanRemaining += getItemSpanSize(parent, i)
            }
        }

        return isOneOfLastItems && totalSpanRemaining <= spanCount - spanIndex
    }
}
