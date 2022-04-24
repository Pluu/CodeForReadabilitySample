package com.pluu.sample.codeforreadability.data

import com.pluu.sample.codeforreadability.model.GenerateItem
import com.pluu.sample.codeforreadability.model.TriggerTreeSet
import com.pluu.sample.codeforreadability.provider.RandomGenerator
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

interface ItemRepository {
    // FIXED 14. modify data type
    val dataFlow: Flow<List<GenerateItem>>

    fun generate(): Result<GenerateItem>

    fun reset()
}

// FIXED 9. use item repository
class ItemRepositoryImpl(
    // FIXED 11. inject random generator
    private val randomGenerator: RandomGenerator
) : ItemRepository {
    // FIXED 13. modify container type
    private val cachedSet = TriggerTreeSet<GenerateItem>(
        TreeSet { p0, p1 ->
            p0.text.compareTo(p1.text)
        }
    )

    // FIXED 14. modify data type
    override val dataFlow: Flow<List<GenerateItem>>
        get() = callbackFlow {
            trySend(cachedSet.toList())

            val listener: () -> Unit = {
                trySend(cachedSet.toList())
            }
            cachedSet.setListener(listener)
            awaitClose { cachedSet.setListener(null) }
        }

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