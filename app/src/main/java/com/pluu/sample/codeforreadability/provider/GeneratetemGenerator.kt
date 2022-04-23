package com.pluu.sample.codeforreadability.provider

import android.graphics.Color
import com.pluu.sample.codeforreadability.model.GenerateItem

interface GenerateItemGenerator {
    fun generate(): GenerateItem
}

class GenerateItemGeneratorImpl : GenerateItemGenerator {
    override fun generate(): GenerateItem = GenerateItem(
        text = ('a' + (0 until 26).random()).toString(),
        bgColor = Color.rgb(
            (0..255).random(),
            (0..255).random(),
            (0..255).random()
        )
    )
}