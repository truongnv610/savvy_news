package com.savvy.app.base.extension

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.*
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.inSpans
import com.savvy.app.base.CustomTypefaceSpan

fun SpannableStringBuilder.textSize(
    @Px size: Int,
    builderAction: SpannableStringBuilder.() -> Unit
) {
    inSpans(AbsoluteSizeSpan(size)) {
        builderAction()
    }
}

fun SpannableStringBuilder.font(
    fontName: String,
    typeface: Typeface,
    builderAction: SpannableStringBuilder.() -> Unit
) {
    inSpans(CustomTypefaceSpan(fontName, typeface)) {
        builderAction()
    }
}

fun SpannableStringBuilder.foregroundColor(color: Int): ForegroundColorSpan =
    ForegroundColorSpan(color)

fun SpannableStringBuilder.backgroundColor(color: Int): BackgroundColorSpan =
    BackgroundColorSpan(color)

inline fun SpannableStringBuilder.clickable(
    crossinline onClick: (View) -> Unit,
): ClickableSpan {
    return object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick(widget)
        }
    }
}

fun SpannableStringBuilder.link(url: String): URLSpan {
    return URLSpan(url)
}

fun SpannableStringBuilder.append(
    context: Context,
    @StringRes textResourceId: Int,
    @ColorRes colorResourceId: Int,
    isBold: Boolean = false,
    isUnderline: Boolean = false,
    click: (() -> Unit)? = null
): SpannableStringBuilder {
    val text = context.getString(textResourceId)
    return append(context, text, colorResourceId, isBold, isUnderline, click)
}

fun SpannableStringBuilder.append(
    context: Context,
    text: String,
    @ColorRes colorResourceId: Int,
    isBold: Boolean = false,
    isUnderline: Boolean = false,
    click: (() -> Unit)? = null
): SpannableStringBuilder {
    val textColor = ContextCompat.getColor(context, colorResourceId)
    val index = length
    when {
        isBold -> bold {
            append(text)
        }
        else -> {
            append(text)
        }
    }

    click?.let {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                it()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = textColor
            }
        }
        setSpan(
            clickableSpan,
            index,
            index + text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    setSpan(
        ForegroundColorSpan(textColor),
        index,
        index + text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    if (isUnderline)
        setSpan(
            UnderlineSpan(),
            index,
            index + text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    return this
}

fun SpannableStringBuilder.appendSpace(): SpannableStringBuilder {
    return append(" ")
}

fun SpannableStringBuilder.appendNewline(): SpannableStringBuilder {
    return append("\n")
}
