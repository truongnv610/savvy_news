package com.savvy.data.base.helper

import java.lang.reflect.Field

object BuildConfigHelper {
    private const val BUILD_CONFIG = "com.savvy.app.base.helper.BuildConfigHelper"
    val DEBUG = debug
    val APPLICATION_ID = getBuildConfigValue("APPLICATION_ID") as String?
    val BUILD_TYPE = getBuildConfigValue("BUILD_TYPE") as String?
    val FLAVOR = flavor
    val VERSION_CODE = versionCode
    val VERSION_NAME = getBuildConfigValue("VERSION_NAME") as String?

    private val debug: Boolean
        get() {
            val isDebug = getBuildConfigValue("DEBUG")
            return if (isDebug != null && isDebug is Boolean) {
                isDebug
            } else {
                false
            }
        }

    private val versionCode: Int
        get() {
            val versionCode = getBuildConfigValue("VERSION_CODE")
            return if (versionCode != null && versionCode is Int) {
                versionCode
            } else {
                Int.MIN_VALUE
            }
        }

    private val flavor: String
        get() {
            val flavor = getBuildConfigValue("FLAVOR")
            return if (flavor != null && flavor is String) {
                flavor
            } else {
                "dev"
            }
        }

    private fun getBuildConfigValue(fieldName: String): Any? {
        return try {
            val classBuildConfig = Class.forName(BUILD_CONFIG)
            val field: Field = classBuildConfig.getDeclaredField(fieldName)
            field.isAccessible = true
            field.get(null)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }
}