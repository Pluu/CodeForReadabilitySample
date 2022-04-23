package com.pluu.sample.codeforreadability.data

import com.pluu.sample.codeforreadability.provider.RandomGenerator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

// FIXED 13. add test ItemRepositoryImpl
@OptIn(ExperimentalTime::class)
internal class ItemRepositoryImplTest {

    private lateinit var itemRepository: ItemRepository

    private val generator: RandomGenerator = mock()

    @Before
    fun setup() {
        itemRepository = ItemRepositoryImpl(generator)
    }

    @Test
    fun generate() {
        // given
        var callCount = 0
        whenever(generator.randomAlphabet())
            .then {
                ('a' + callCount++).toString()
            }

        // when/then
        repeat(26) {
            val result = itemRepository.generate()
            assertTrue(result.isSuccess)
            assertEquals(('a' + it).toString(), result.getOrThrow().text)
        }
    }

    @Test
    fun reset() {
        // 기본 길이 체크
        assertTrue(itemRepository.data.isEmpty())

        // generate 후 길이 체크
        whenever(generator.randomAlphabet())
            .thenReturn("0")
        itemRepository.generate()
        assertEquals(1, itemRepository.data.size)

        // reset 후 길이 체크
        itemRepository.reset()
        assertTrue(itemRepository.data.isEmpty())
    }
}