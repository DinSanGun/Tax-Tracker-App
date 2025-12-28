package com.dinyairsadot.taxtracker.core.ui

import androidx.compose.ui.graphics.Color
import android.graphics.Color.parseColor

const val DEFAULT_CATEGORY_COLOR_HEX = "#424242" // dark grey

fun parseCategoryColorOrDefault(hex: String?): Color {
    return try {
        Color(parseColor(hex ?: DEFAULT_CATEGORY_COLOR_HEX))
    } catch (e: IllegalArgumentException) {
        Color(parseColor(DEFAULT_CATEGORY_COLOR_HEX))
    }
}
