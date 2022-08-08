package com.savvy.data.base.extension

import android.telephony.PhoneNumberUtils
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

private val FORMATTER_DEFAULT =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())

fun String.toZonedDateTime(formatter: DateTimeFormatter = FORMATTER_DEFAULT): ZonedDateTime = ZonedDateTime.parse(this, formatter)

fun String.toPhoneNumber() = PhoneNumberUtils.formatNumber(this,  "", "TH").replace(" ", "-")