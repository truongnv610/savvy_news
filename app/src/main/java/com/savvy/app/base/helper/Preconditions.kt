package com.savvy.app.base.helper

object Preconditions {
    fun atLeastOneNotNull(onSuccess: (() -> Unit)?, onInvalid: ((String) -> Unit)?) {
        if (onSuccess == null && onInvalid == null) {
            throw IllegalArgumentException("At least one of onSuccess or onInvalid should be present")
        }
    }
}
