package com.pluu.sample.codeforreadability.provider

import android.graphics.Color
import com.pluu.sample.codeforreadability.model.SampleItem

interface SampleItemGenerator {
    fun generate(): SampleItem
}

class SampleItemGeneratorImpl : SampleItemGenerator {
    override fun generate(): SampleItem = SampleItem(
        text = ('a' + (0 until 26).random()).toString(),
        bgColor = Color.rgb(
            (0..255).random(),
            (0..255).random(),
            (0..255).random()
        )
    )
}