package com.savvy.domain.provider

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.savvy.domain.base.extension.wrap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.nio.charset.StandardCharsets
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val locale: Locale
) {
    companion object {
        @JvmStatic
        private val TAG = PreferenceProvider::class.java.simpleName

        const val KEY_LANGUAGE_CODE = "language_code"
        const val KEY_WRAP_API_ENDPOINT = "WRAP_API_ENDPOINT"
        const val IS_FIRST_TIME_OPEN_APP = "IS_FIRST_TIME_OPEN_APP"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val LOGIN_INFO = "LOGIN_INFO"
    }

    val languageCode: String
        get() = sharedPreferences.getString(KEY_LANGUAGE_CODE, locale.language) ?: locale.language

    val currentLocale: Locale
        get() = Locale(languageCode)

    val wrapContext: Context
        get() = context.wrap(currentLocale)

    val isFirstTimeOpenApp: Boolean
        get() = sharedPreferences.getBoolean(IS_FIRST_TIME_OPEN_APP, true)

    val isNotLoggedIn: Boolean
        get() = getAccessToken().isEmpty()

    fun updateLanguageCode(languageCode: String) {
        sharedPreferences.edit()
            .putString(KEY_LANGUAGE_CODE, languageCode)
            .apply()
    }

    fun updateWrapApiEndpoint(endpoint: String) {
        sharedPreferences.edit()
            .putString(KEY_WRAP_API_ENDPOINT, endpoint)
            .apply()
    }

    fun updateFirstTimeOpenApp(isFirstTimeOpenApp: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_FIRST_TIME_OPEN_APP, isFirstTimeOpenApp)
            .apply()
    }

    fun saveAccessToken(accessToken: String) {
        val data = accessToken.toByteArray(StandardCharsets.UTF_8)
        val accessTokenEncode = Base64.encodeToString(data, Base64.NO_WRAP)
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, accessTokenEncode)
            .apply()
    }

    fun getAccessToken(): String {
        val accessTokenEncode = sharedPreferences.getString(ACCESS_TOKEN, "")
        return if (accessTokenEncode.isNullOrEmpty()) {
            ""
        } else {
            val data = Base64.decode(accessTokenEncode, Base64.NO_WRAP)
            String(data, StandardCharsets.UTF_8)
        }
    }
}