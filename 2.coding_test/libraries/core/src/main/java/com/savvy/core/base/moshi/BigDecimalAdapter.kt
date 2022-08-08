package com.savvy.core.base.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.math.BigDecimal
import java.math.RoundingMode

class BigDecimalAdapter: JsonAdapter<BigDecimal>() {
    override fun fromJson(reader: JsonReader): BigDecimal? {
        return if (reader.peek() == JsonReader.Token.STRING) {
            BigDecimal(reader.nextString())
        } else {
            BigDecimal.valueOf(reader.nextDouble())
        }
    }

    override fun toJson(writer: JsonWriter, value: BigDecimal?) {
        writer.value(value?.setScale(2, RoundingMode.HALF_EVEN))
    }
}