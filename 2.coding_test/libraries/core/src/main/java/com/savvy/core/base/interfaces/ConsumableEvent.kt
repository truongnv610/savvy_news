package com.savvy.core.base.interfaces

import androidx.lifecycle.ViewModel

interface ConsumableEvent {
    val viewModelList: List<ViewModel>
        get() = mutableListOf()
}