
package com.savvy.app.base.helper

import androidx.annotation.Keep
import com.savvy.app.BuildConfig

@Keep
object BuildConfigHelper {
    val DEBUG: Boolean = BuildConfig.DEBUG
    val APPLICATION_ID: String = BuildConfig.APPLICATION_ID
    val BUILD_TYPE: String = BuildConfig.BUILD_TYPE
    val FLAVOR: String = BuildConfig.FLAVOR
    val VERSION_CODE: Int = BuildConfig.VERSION_CODE
    val VERSION_NAME: String = BuildConfig.VERSION_NAME
    val BASE_URL: String = BuildConfig.BASE_URL
    val API_KEY: String = BuildConfig.API_KEY
}
