package com.savvy.core.base.epoxy.carousel

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true)
class OneColumnHorizontalCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Carousel(context, attrs, defStyleAttr) {
    init {
        isNestedScrollingEnabled = false
    }

    override fun createLayoutManager(): LayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}
