package com.savvy.domain.base.extension

import android.webkit.MimeTypeMap
import org.jsoup.Jsoup

private const val PHONE_NUMBER_CODE = "+66"

fun String.formatPhoneNumber(): String {
    return when {
        this.startsWith("0") -> {
            this.replaceFirst("0", PHONE_NUMBER_CODE)
        }
        this.startsWith("66") -> {
            "+$this"
        }
        else -> this
    }
}

fun String.formatPhoneNumberStartWithZero(): String {
    return when {
        this.startsWith("+66") -> {
            this.replaceFirst("+66", "0")
        }
        this.startsWith("66") -> {
            this.replaceFirst("66", "0")
        }
        else -> this
    }
}

fun String.getMimeTypeForUrlOrFileName(): String? {
    var type: String? = null
    val extension = MimeTypeMap.getFileExtensionFromUrl(this)
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    return type
}

val String.removeHtmlTag: String
    get() {
        return Jsoup.parse(this).text()
    }
