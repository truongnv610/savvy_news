package com.savvy.app.di

import android.content.Context
import com.savvy.app.BuildConfig
import com.savvy.app.base.interceptor.NetworkExceptionInterceptor
import com.savvy.core.base.moshi.*
import com.savvy.data.base.service.*
import com.savvy.domain.SchedulersFacade
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.SerializeOnly
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.ZonedDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        @JvmStatic
        private val TAG = NetworkModule::class.java.simpleName

        const val NEWS_URL = "https://newsapi.org/v2/"
        const val NEWS_HTTP_CLIENT = "NEWS_HTTP_CLIENT"
        const val NEWS_RETROFIT = "NEWS_RETROFIT"
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(DeserializeOnly.ADAPTER_FACTORY)
        .add(SerializeOnly.ADAPTER_FACTORY)
        .add(ZonedDateTime::class.java, ZonedDateTimeAdapter())
//        .add(CMSProductType::class.java, CMSPRoductTypeAdapter())
        .add(DefaultIfNullFactory())
        .add(BigDecimal::class.java, BigDecimalAdapter())
//        .add(PromotionAdapter)
        .build()

    @Singleton
    @Provides
    fun provideInterceptor() =
        HttpLoggingInterceptor { message -> Timber.tag(TAG).d(message) }.apply {
            setLevel(
                when {
                    BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                    else -> HttpLoggingInterceptor.Level.NONE
                }
            )
        }

    @Singleton
    @Provides
    fun provideNetworkExceptionInterceptor(@ApplicationContext context: Context) =
        NetworkExceptionInterceptor(context)

    @Singleton
    @Provides
    @Named(NEWS_HTTP_CLIENT)
    fun provideNewsHttpClient(
        networkExceptionInterceptor: NetworkExceptionInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(networkExceptionInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    @Named(NEWS_RETROFIT)
    fun provideNewsRetrofit(
        moshi: Moshi,
        @Named(NEWS_HTTP_CLIENT) okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(NEWS_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideSchedulerFacade() = SchedulersFacade()

    @Provides
    fun provideNewsService(@Named(NEWS_RETROFIT) retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

}
