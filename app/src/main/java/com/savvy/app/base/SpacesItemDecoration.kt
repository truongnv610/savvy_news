package com.savvy.app.base

import android.content.Context
import android.graphics.Rect
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.*
import com.savvy.core.base.BaseItemDecoration

/**
 * Created by CThawanapong on 23/1/2018 AD.
 * Email: c.thawanapong@gmail.com
 */
class SpacesItemDecoration(private val spacing: Int = 0) : BaseItemDecoration() {
    companion object {
        @JvmStatic
        private val VERTICAL = OrientationHelper.VERTICAL
    }

    private var orientation = -1
    private var spanCount = -1
    private var halfSpacing = -1

    init {
        halfSpacing = spacing / 2
    }

    constructor(context: Context, @DimenRes spacingDimen: Int) : this(context.resources.getDimensionPixelSize(spacingDimen))

    override fun setSpacings(
        outRect: Rect,
        parent: RecyclerView,
        childCount: Int,
        childIndex: Int,
        childViewHolder: RecyclerView.ViewHolder,
        itemSpanSize: Int,
        spanIndex: Int
    ) {
        outRect.top = halfSpacing
        outRect.bottom = halfSpacing
        outRect.left = halfSpacing
        outRect.right = halfSpacing

        if (isTopEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            outRect.top = spacing
        }

        if (isLeftEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            outRect.left = spacing
        }

        if (isRightEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            outRect.right = spacing
        }

        if (isBottomEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            outRect.bottom = spacing
        }
    }
}
