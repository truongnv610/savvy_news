package com.savvy.app.base.initializer

import android.content.Context
import android.os.Handler
import androidx.startup.Initializer
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.savvy.data.base.di.ASYNC_BACKGROUND_THREAD_HANDLER
import com.savvy.app.di.entry_point.InitializerEntryPoint
import javax.inject.Inject
import javax.inject.Named

class EpoxyControllerInitializer : Initializer<Unit> {
    @Inject
    @Named(ASYNC_BACKGROUND_THREAD_HANDLER)
    lateinit var handler: Handler

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
            .inject(this)

        EpoxyController.defaultDiffingHandler = handler
        EpoxyController.defaultModelBuildingHandler = handler
        Carousel.setDefaultGlobalSnapHelperFactory(null)
        Carousel.setDefaultItemSpacingDp(0)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }
}
