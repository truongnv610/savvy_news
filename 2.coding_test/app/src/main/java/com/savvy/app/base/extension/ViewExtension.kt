package com.savvy.app.base.extension

import android.view.View
import androidx.annotation.IdRes

fun <T : View> View.findParentViewById(@IdRes viewId: Int): T? {
    @Suppress("UNCHECKED_CAST")
    return when (this.id) {
        viewId -> this as? T
        else -> (parent as? View)?.findParentViewById<T>(viewId)
    }
}
