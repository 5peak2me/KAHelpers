package com.crazylegend.kotlinextensions.views

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat


/**
 * Created by hristijan on 2/22/19 to long live and prosper !
 */

/**
 * UnderLine the TextView.
 */
fun TextView.underLine() {
    paint.flags = paint.flags or Paint.UNDERLINE_TEXT_FLAG
    paint.isAntiAlias = true
}

/**
 * DeleteLine for a TextView.
 */
fun TextView.deleteLine() {
    paint.flags = paint.flags or Paint.STRIKE_THRU_TEXT_FLAG
    paint.isAntiAlias = true
}


/**
 * Bold the TextView.
 */
fun TextView.bold() {
    paint.isFakeBoldText = true
    paint.isAntiAlias = true
}


/**
 * Set font for TextView.
 */
fun TextView.font(font: String) {
    typeface = Typeface.createFromAsset(context.assets, "fonts/$font.ttf")
}


/**
 * Set different color for substring TextView.
 */
fun TextView.setColorOfSubstring(substring: String, color: Int) {
    try {
        val spannable = android.text.SpannableString(text)
        val start = text.indexOf(substring)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), start, start + substring.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = spannable
    } catch (e: Exception) {
        Log.d("ViewExtensions",  "exception in setColorOfSubstring, text=$text, substring=$substring", e)
    }
}

/**
 * Set a drawable to the left of a TextView.
 */
fun TextView.setDrawableLeft(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
}

/**
 * Set a drawable to the top of a TextView.
 */
fun TextView.setDrawableTop(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, drawable, 0, 0)
}


/**
 * Set a drawable to the right of a TextView.
 */
fun TextView.setDrawableRight(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
}

/**
 * Set a drawable to the bottom of a TextView.
 */
fun TextView.setDrawableBottom(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawable)
}


fun TextView.sizeSpan(str: String, range: IntRange, scale: Float = 1.5f){
    text = str.toSizeSpan(range, scale)
}


fun TextView.colorSpan(str: String, range: IntRange, color: Int = Color.RED){
    text = str.toColorSpan(range, color)
}


fun TextView.backgroundColorSpan(str: String, range: IntRange, color: Int = Color.RED){
    text = str.toBackgroundColorSpan(range, color)
}

fun TextView.strikeThrougthSpan(str: String, range: IntRange){
    text = str.toStrikeThroughSpan(range)
}

fun TextView.clickSpan(str: String, range: IntRange,
                       color: Int = Color.RED, isUnderlineText: Boolean = false, clickListener: View.OnClickListener){
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT  // remove click bg color
    text = str.toClickSpan(range, color, isUnderlineText, clickListener)
}

