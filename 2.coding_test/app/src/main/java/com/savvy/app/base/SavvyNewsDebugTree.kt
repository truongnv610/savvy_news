package com.savvy.app.base

import android.util.Log
import hu.akarnokd.rxjava3.debug.RxJavaAssemblyException
import timber.log.Timber

class SavvyNewsDebugTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)
        when (priority) {
            Log.ERROR,
            Log.WARN -> {
                val enhancedThrowable =
                    RxJavaAssemblyException.find(t ?: Throwable()) ?: t ?: Throwable()
               // chuckerCollector.onError(tag ?: "", enhancedThrowable)
            }
            else -> {
            }
        }
    }
}
