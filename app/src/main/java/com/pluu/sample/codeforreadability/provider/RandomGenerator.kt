package com.pluu.sample.codeforreadability.provider

import android.graphics.Color

class RandomGenerator {
    private val textRandomRange = (0 until 26)

    private val colorRandomRange = (0..255)

    fun randomAlphabet(): String =
        ('a' + textRandomRange.random()).toString()

    fun randomColor(): Int =
        Color.rgb(
            colorRandomRange.random(),
            colorRandomRange.random(),
            colorRandomRange.random()
        )
} 
