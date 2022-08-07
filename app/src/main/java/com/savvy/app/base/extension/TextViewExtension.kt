package com.savvy.app.base.extension

import android.graphics.Paint
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.text.util.Linkify
import android.util.Patterns
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.util.LinkifyCompat
import com.savvy.app.R

fun TextView.removeUnderlines() {
    this.paintFlags = this.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
    val spannable = SpannableString(text)
    val spans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
    spans.forEach { urlSpan ->
        spannable.setSpan(
            object : URLSpan(urlSpan.url) {
                override fun updateDrawState(drawState: TextPaint) {
                    super.updateDrawState(drawState)
                    drawState.isUnderlineText = false
                    drawState.color = ContextCompat.getColor(this@removeUnderlines.context, R.color.colorPrimary)
                }
            },
            spannable.getSpanStart(urlSpan), spannable.getSpanEnd(urlSpan), Spanned.SPAN_MARK_MARK
        )
    }
    text = spannable
}

fun TextView.makePhoneLinksClickable() {
    LinkifyCompat.addLinks(this, Linkify.PHONE_NUMBERS)
    // For some phone manufacturers (e.g. Moto G5 plus and some samsung devices)
    // Linkify.ALL does not work for phonenumbers
    // therefore we added the next line, to fix that:
    LinkifyCompat.addLinks(
        this, Patterns.PHONE, "tel:",
        Linkify.sPhoneNumberMatchFilter,
        Linkify.sPhoneNumberTransformFilter
    )
    this.movementMethod = LinkMovementMethod.getInstance()
}

fun TextView.disableCopyPaste() {
    isLongClickable = false
    setTextIsSelectable(false)
    customSelectionActionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu): Boolean {
            return false
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {}
    }
}
