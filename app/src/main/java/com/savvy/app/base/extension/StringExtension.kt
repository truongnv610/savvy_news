package com.savvy.app.base.extension

import android.util.Patterns

import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val PARTS_DELIMITER = ";"
private const val VALUE_DELIMITER = "="
private const val UTF8 = "UTF-8"
private const val CHARSET = "charset"
private val REGEX_CONTENT_DESCRIPTION = Pattern.compile("\\\\?\\{.*?\\}")
private val REGEX_YOUTUBE_LINK = Pattern.compile("(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*")

fun String.isValidPhoneNumber(): Boolean {
    val phoneRegex = "((\\+66|66|0)(\\d{9}))"
    return Pattern.compile(phoneRegex).matcher(this).matches()
}

val String.toDisplayPhoneNumber: String
    get() {
        return when {
            length != 10 -> this
            else -> "${substring(0, 3)}-${substring(3, 6)}-${substring(6)}"
        }
    }

val String.toDisplayPartOfPhoneNumber: String
    get() {
        return when {
            this.startsWith("+66") -> {
                "${substring(0, 5)}-XXX-XX${substring(10)}"
            }
            length != 10 -> this
            else -> "${substring(0, 3)}-XXX-XX${substring(8)}"
        }
    }

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this.trim()).matches()
}

fun String.isValidPassword(): Boolean {
    return if (this.length > 7) {
        val regexPassword = "^(?=.*([0-9]|[!@#\$%^]))(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$"
        Pattern.compile(regexPassword).matcher(this).matches()
    } else {
        false
    }
}

fun String.isValidCardId(): Boolean {
    var multiNum = 13
    var strDigit = 0
    var result = 0
    if (this.length != 13) {
        return false
    }
    while (multiNum != 1) {
        if (this[strDigit].toString().toIntOrNull() == null) {
            return false
        }
        result += this[strDigit].toString().toInt() * multiNum
        multiNum -= 1
        strDigit += 1
    }
    result %= 11

    if (result == 0) result = 10

    result = 11 - result

    if (result == 10) result = 0

    return result == this[12].toString().toInt()
}

val String.getMimeType: String
    get() {
        if (this.contains(PARTS_DELIMITER)) {
            val contentTypeParts = this.split(PARTS_DELIMITER.toRegex())
            return contentTypeParts.first().trim()
        }
        return this
    }

val String.getCharset: String
    get() {
        if (this.contains(PARTS_DELIMITER)) {
            val contentTypeParts = this.split(PARTS_DELIMITER.toRegex())
            val charsetParts = contentTypeParts[1].split(VALUE_DELIMITER.toRegex())
            if (charsetParts.first().trim().startsWith(CHARSET)) {
                return charsetParts[1].trim().uppercase(Locale.getDefault())
            }
        }
        return UTF8
    }

val String.contentMarkdown: String
    get() {
        return replace(REGEX_CONTENT_DESCRIPTION.toRegex(), "")
    }

val String.getYoutubeVideoId: String
    get() {
        val matcher: Matcher = REGEX_YOUTUBE_LINK.matcher(this)
        return when (matcher.find()) {
            true -> matcher.group()
            else -> ""
        }
    }
