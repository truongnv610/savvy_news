package com.savvy.app.base.extension

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

import java.util.*

const val patternDestination = "MMM dd, HH:mm"
val simpleDateFormatDestination = SimpleDateFormat(patternDestination, Locale.ENGLISH)
@SuppressLint("SimpleDateFormat")
val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

fun String.displayDateTime(): String {
    try {
        val date = format.parse(this)
        return simpleDateFormatDestination.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return this
}