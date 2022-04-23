package com.pluu.sample.codeforreadability.data

import com.pluu.sample.codeforreadability.model.GenerateItem
import com.pluu.sample.codeforreadability.provider.RandomGenerator

interface ItemRepository {
    val data: List<GenerateItem>

    fun generate(): Result<GenerateItem>

    fun reset()
}

// FIXED 9. use item repository
class ItemRepositoryImpl(
    // FIXED 11. inject random generator
    private val randomGenerator: RandomGenerator
) : ItemRepository {
    private val cachedList = mutableListOf<GenerateItem>()

    override val data: List<GenerateItem>
        get() = cachedList

    override fun generate(): Result<GenerateItem> {
        val item = GenerateItem(
            text = randomGenerator.randomAlphabet(),
            bgColor = randomGenerator.randomColor()
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