package com.savvy.data.base.converter

import io.objectbox.converter.PropertyConverter
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class ZonedDateTimeConverter : PropertyConverter<ZonedDateTime, String> {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override fun convertToDatabaseValue(entityProperty: ZonedDateTime?): String {
        return (entityProperty ?: ZonedDateTime.now()).format(formatter)
    }

    override fun convertToEntityProperty(databaseValue: String?): ZonedDateTime {
        return databaseValue?.let {
            formatter.parse(it, ZonedDateTime::from)
        } ?: ZonedDateTime.now()
    }
}
