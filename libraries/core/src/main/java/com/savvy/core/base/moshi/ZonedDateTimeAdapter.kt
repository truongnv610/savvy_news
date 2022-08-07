package com.savvy.core.base.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.threeten.bp.DateTimeException
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter.ISO_ZONED_DATE_TIME


class ZonedDateTimeAdapter : JsonAdapter<ZonedDateTime>() {
    override fun toJson(writer: JsonWriter, value: ZonedDateTime?) {
        val formatter = ISO_ZONED_DATE_TIME
        writer.value(formatter.format(value))
    }

    override fun fromJson(reader: JsonReader): ZonedDateTime {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.skipValue()
            return ZonedDateTime.now(ZoneOffset.UTC)
        }

        val formatter = ISO_ZONED_DATE_TIME
        val value = reader.nextString()
        return try {
            ZonedDateTime.parse(value, formatter)
        } catch (ignored: DateTimeException) {
            ZonedDateTime.now(ZoneOffset.UTC)
        }
    }
}