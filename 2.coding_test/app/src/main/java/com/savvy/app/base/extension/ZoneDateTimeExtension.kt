package com.savvy.app.base.extension

import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

private val FORMATTER_PROMOTION =
    DateTimeFormatter.ofPattern("dd MMM yy").withZone(ZoneId.systemDefault())

private val FORMATTER_PROMOTION_COUPON =
    DateTimeFormatter.ofPattern("dd MMM yyyy").withZone(ZoneId.systemDefault())

private val FORMATTER_PROMOTION_COUPON_FIRST =
    DateTimeFormatter.ofPattern("dd MMM").withZone(ZoneId.systemDefault())

private val FORMATTER_PROMOTION_COUPON_TIME =
    DateTimeFormatter.ofPattern("hh.mm").withZone(ZoneId.systemDefault())

private val FORMATTER_DEFAULT =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())

private val FORMATTER_DD_MMM_YY =
    DateTimeFormatter.ofPattern("dd MMM yy").withZone(ZoneId.systemDefault())

private val FORMATTER_DD_MMM_YYYY =
    DateTimeFormatter.ofPattern("dd MMM yyyy").withZone(ZoneId.systemDefault())

private val FORMATTER_DD_MMM =
    DateTimeFormatter.ofPattern("dd MMM").withZone(ZoneId.systemDefault())

private val FORMATTER_QUOTATION_ITEM =
    DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm").withZone(ZoneId.systemDefault())

val ZonedDateTime.displayPromotionPeriodDate: String
    get() = this.format(
        FORMATTER_PROMOTION
            .withChronology(ThaiBuddhistChronology.INSTANCE)
            .withLocale(
                Locale("th", "TH")
            )
    )

val ZonedDateTime.displayPromotionCouponDate: String
    get() = this.format(
        FORMATTER_PROMOTION_COUPON
            .withChronology(ThaiBuddhistChronology.INSTANCE)
            .withLocale(
                Locale("th", "TH")
            )
    )

val ZonedDateTime.displayPromotionCouponDateFirst: String
    get() = this.format(
        FORMATTER_PROMOTION_COUPON_FIRST
            .withChronology(ThaiBuddhistChronology.INSTANCE)
            .withLocale(
                Locale("th", "TH")
            )
    )

val ZonedDateTime.displayPromotionCouponTime: String
    get() = this.format(
        FORMATTER_PROMOTION_COUPON_TIME
            .withChronology(ThaiBuddhistChronology.INSTANCE)
            .withLocale(
                Locale("th", "TH")
            )
    )

val ZonedDateTime.displayQuotationDate: String
    get() = this.format(
        FORMATTER_QUOTATION_ITEM
            .withChronology(ThaiBuddhistChronology.INSTANCE)
            .withLocale(Locale("th", "TH"))
    )

fun String.toZonedDateTime(formatter: DateTimeFormatter = FORMATTER_DEFAULT): ZonedDateTime =
    ZonedDateTime.parse(this, formatter)

fun ZonedDateTime.displayDDMMMYY(locale: Locale = Locale("th", "TH")): String {
    return format(
        FORMATTER_DD_MMM_YY
            .withChronology(ThaiBuddhistChronology.INSTANCE)
            .withLocale(
                locale
            )
    )
}

fun ZonedDateTime.displayDDMMMYYYY(locale: Locale = Locale("th", "TH")): String {
    return format(
        FORMATTER_DD_MMM_YYYY
            .withChronology(ThaiBuddhistChronology.INSTANCE)
            .withLocale(
                locale
            )
    )
}

fun ZonedDateTime.displayDDMMM(): String {
    return format(
        FORMATTER_DD_MMM
            .withChronology(ThaiBuddhistChronology.INSTANCE)
    )
}

val String.convertZonedDateTime: ZonedDateTime
    get() {
        return ZonedDateTime.parse(this)
    }
