package com.pluu.sample.codeforreadability.data

import com.pluu.sample.codeforreadability.model.GenerateItem
import com.pluu.sample.codeforreadability.provider.RandomGenerator
import java.util.*

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
    // FIXED 13. modify container type
    private val cachedSet = TreeSet<GenerateItem>(
        TreeSet { p0, p1 ->
            p0.text.compareTo(p1.text)
        }
    )

    override val data: List<GenerateItem>
        get() = cachedSet.toList()

    override fun generate(): Result<GenerateItem> {
        val item = GenerateItem(
            text = randomGenerator.randomAlphabet(),
            bgColor = randomGenerator.randomColor()
        )
        return if (cachedSet.add(item)) {
            Result.success(item)
        } else {
            Result.failure(IllegalStateException("Duplicate Item : ${item.text}"))
        }
    }

    override fun reset() {
        cachedSet.clear()
    }
}