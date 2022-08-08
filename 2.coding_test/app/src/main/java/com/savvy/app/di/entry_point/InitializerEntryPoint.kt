package com.savvy.app.di.entry_point

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import com.savvy.app.base.initializer.EpoxyControllerInitializer
import com.savvy.app.base.initializer.TimberInitializer

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {
    companion object {
        // a helper method to resolve the InitializerEntryPoint from the context
        fun resolve(context: Context): InitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint::class.java
            )
        }
    }

    fun inject(initializer: TimberInitializer)
    fun inject(initializer: EpoxyControllerInitializer)
}
