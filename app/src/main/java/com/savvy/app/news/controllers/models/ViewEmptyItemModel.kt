package com.savvy.app.news.controllers.models

import android.content.Context
import com.airbnb.epoxy.EpoxyModelClass
import com.savvy.app.R
import com.savvy.app.databinding.ViewEmptyItemBinding
import com.savvy.core.base.epoxy.EpoxyViewBindingModelWithHolder

@EpoxyModelClass(layout = R.layout.view_empty_item)
abstract class ViewEmptyItemModel : EpoxyViewBindingModelWithHolder<ViewEmptyItemBinding>() {
    override fun ViewEmptyItemBinding.bind(context: Context) {

    }
}