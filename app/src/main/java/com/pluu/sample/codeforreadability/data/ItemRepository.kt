package com.pluu.sample.codeforreadability.data

import android.graphics.Color
import com.pluu.sample.codeforreadability.model.GenerateItem

interface ItemRepository {
    val data: List<GenerateItem>

    fun generate(): Result<GenerateItem>

    fun reset()
}

// FIXED 9. use item repository
class ItemRepositoryImpl : ItemRepository {
    private val cachedList = mutableListOf<GenerateItem>()

    override val data: List<GenerateItem>
        get() = cachedList

    override fun generate(): Result<GenerateItem> {
        val item = GenerateItem(
            text = ('a' + (0 until 26).random()).toString(),
            bgColor = Color.rgb(
                (0..255).random(),
                (0..255).random(),
                (0..255).random()
            )
        )
        return if (cachedList.none { it.text == item.text }) {
            cachedList.add(item)
            Result.success(item)
        } else {
            Result.failure(IllegalStateException("Duplicate Item : ${item.text}"))
        }
    }

    override fun reset() {
        cachedList.clear()
    }
}