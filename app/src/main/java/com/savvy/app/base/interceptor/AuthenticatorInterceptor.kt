package com.savvy.app.base.interceptor

import com.savvy.domain.provider.PreferenceProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticatorInterceptor(
    private val preferenceProvider: PreferenceProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val chainBuilder = chain.request().newBuilder()
        val request = if (preferenceProvider.getAccessToken().isEmpty()) {
            chainBuilder.build()
        } else {
            chainBuilder
                .addHeader("Authorization", "Bearer ${preferenceProvider.getAccessToken()}")
                .build()
        }
        return chain.proceed(request)
    }
}
