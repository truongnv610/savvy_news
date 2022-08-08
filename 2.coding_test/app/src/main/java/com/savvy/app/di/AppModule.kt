package com.savvy.app.di

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.SharedPreferences
import android.os.Handler
import android.view.WindowManager
import androidx.annotation.XmlRes
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.GlidePreloadRequestHolder
import com.airbnb.epoxy.glidePreloader
import com.airbnb.epoxy.preload.EpoxyModelPreloader
import com.airbnb.epoxy.preload.ViewMetadata
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.savvy.app.BuildConfig
import com.savvy.app.R
import com.savvy.app.base.SavvyNewsDebugTree
import com.savvy.app.base.extension.loadImage
import com.savvy.core.base.epoxy.EpoxyViewBindingModelWithHolder
import com.savvy.data.base.di.APPLICATION_ID
import com.savvy.data.base.di.ASYNC_BACKGROUND_THREAD_HANDLER
import com.savvy.data.base.di.REMOTE_CONFIG_DEFAULTS
import com.savvy.data.base.di.VERSION_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    companion object {
        const val SHARE_PREF = "share_pref"
        private const val MINIMUM_FETCH_INTERVAL = 60L
        private const val MINIMUM_FETCH_INTERVAL_PROD = 600L
    }

    @Singleton
    @Provides
    @Named(APPLICATION_ID)
    fun provideApplicationId(): String = BuildConfig.APPLICATION_ID

    @Singleton
    @Provides
    @Named(VERSION_NAME)
    fun provideVersionName(): String = BuildConfig.VERSION_NAME

    @Singleton
    @Provides
    @Named(REMOTE_CONFIG_DEFAULTS)
    @XmlRes
    fun provideRemoteConfigDefault(): Int = R.xml.remote_config_defaults

    @Singleton
    @Provides
    fun provideTree(
    ): Timber.Tree {
        return SavvyNewsDebugTree()
    }
//
//    @Singleton
//    @Provides
//    fun provideCrashlytics() = Firebase.crashlytics
//
//    @Singleton
//    @Provides
//    fun provideRemoteConfigSettings() = FirebaseRemoteConfigSettings.Builder()
//        .apply {
//            minimumFetchIntervalInSeconds = when {
//                BuildConfig.DEBUG -> MINIMUM_FETCH_INTERVAL
//                else -> MINIMUM_FETCH_INTERVAL_PROD
//            }
//        }
//        .build()
//
//    @Singleton
//    @Provides
//    fun provideFirebaseRemoteConfig() = Firebase.remoteConfig

    @Singleton
    @Provides
    fun provideLocale(): Locale = Locale.getDefault()

    @Singleton
    @Provides
    fun provideEncryptedSharePreference(@ApplicationContext context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            SHARE_PREF,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Singleton
    @Provides
    fun provideNumberFormat(locale: Locale): NumberFormat = DecimalFormat.getInstance(locale)

    @Singleton
    @Provides
    fun provideWindowManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    @Singleton
    @Provides
    fun provideEpoxyModelPreloader(): EpoxyModelPreloader<EpoxyViewBindingModelWithHolder<*>, ViewMetadata?, GlidePreloadRequestHolder> {
        return glidePreloader { requestManager, epoxyModel, _ ->
            epoxyModel.preloaderImages()
                .map { imageUrl ->
                    requestManager.loadImage(imageUrl)
                }
                .firstOrNull() ?: requestManager.loadImage("")
        }
    }

    @Singleton
    @Provides
    @Named(ASYNC_BACKGROUND_THREAD_HANDLER)
    fun provideEpoxyAsyncBackgroundHandler(): Handler = EpoxyAsyncUtil.getAsyncBackgroundHandler()

    @Singleton
    @Provides
    fun provideFusedLocation(@ApplicationContext context: Context): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun provideDownloadManager(@ApplicationContext context: Context): DownloadManager =
        context.getSystemService(
            DOWNLOAD_SERVICE
        ) as DownloadManager
}
