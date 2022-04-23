package com.pluu.sample.codeforreadability.provider

import android.graphics.Color
import com.pluu.sample.codeforreadability.model.ColorValue

interface RandomGenerator {
    fun randomAlphabet(): String

    fun randomColor(): ColorValue
}

class RandomGeneratorImpl : RandomGenerator {
    private val textRandomRange = (0 until 26)

    private val colorRandomRange = (0..255)

    override fun randomAlphabet(): String =
        ('a' + textRandomRange.random()).toString()

    // FIXED 12. modify instance type
    override fun randomColor(): ColorValue = ColorValue(
        Color.rgb(
            colorRandomRange.random(),
            colorRandomRange.random(),
            colorRandomRange.random()
        )
    )
} 
