package com.savvy.app

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import com.savvy.domain.provider.PreferenceProvider
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : MultiDexApplication(), Configuration.Provider {
    companion object {
        @JvmStatic
        private val TAG = MyApplication::class.java.simpleName
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var preferenceProvider: PreferenceProvider

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}
