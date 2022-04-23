package com.pluu.sample.codeforreadability.model

import androidx.core.graphics.ColorUtils

data class SampleItem(
    val text: String,
    val bgColor: Int,
    // FIXED 7. add field favorite
    val isFavorite: Boolean = false
) {
    fun isDarkBg(): Boolean {
        return ColorUtils.calculateLuminance(bgColor) < 0.5
    }
}