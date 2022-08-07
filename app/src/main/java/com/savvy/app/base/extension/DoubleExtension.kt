package com.savvy.app.base.extension

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

val Double.displayPriceNoUnit: String
    get() {
        val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            minimumFractionDigits = when {
                this@displayPriceNoUnit % 1.0 != 0.0 -> 2
                else -> 0
            }
            maximumFractionDigits = 2
        }
        val df = (numberFormat as DecimalFormat)

        return df.format(this)
    }
