package com.savvy.app.base.initializer

import android.content.Context
import androidx.startup.Initializer
import com.savvy.app.di.entry_point.InitializerEntryPoint
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer : Initializer<Unit> {

    @Inject
    lateinit var tree: Timber.Tree

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
            .inject(this)

        Timber.plant(tree)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }
}
