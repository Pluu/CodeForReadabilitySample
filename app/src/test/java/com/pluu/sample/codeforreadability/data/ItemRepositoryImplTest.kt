package com.pluu.sample.codeforreadability.data

import com.pluu.sample.codeforreadability.provider.RandomGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

// FIXED 13. add test ItemRepositoryImpl
@OptIn(ExperimentalCoroutinesApi::class)
internal class ItemRepositoryImplTest {

    private lateinit var itemRepository: ItemRepository

    private val generator: RandomGenerator = mock()

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        itemRepository = ItemRepositoryImpl(generator)

        var callAlphabet = 0
        whenever(generator.randomAlphabet())
            .then {
                (callAlphabet++).toString()
            }

        var callColor = 0
        whenever(generator.randomColor())
            .then {
                callColor++
            }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun generate() = runTest {
        // given
        val list = itemRepository.dataFlow.first()
        // 기본 길이 체크
        assertTrue(list.isEmpty())

        // when/then
        repeat(2) { index ->
            itemRepository.generate()

            val listAfterGenerate = itemRepository.dataFlow.first()
            assertEquals(index + 1, listAfterGenerate.size)
            val result = listAfterGenerate.last()
            assertEquals(index.toString(), result.text)
        }
    }

    @Test
    fun reset() = runTest {
        // given
        val list = itemRepository.dataFlow.first()
        // 기본 길이 체크
        assertTrue(list.isEmpty())

        // generate 후 길이 체크
        itemRepository.generate()
        assertEquals(1, itemRepository.dataFlow.first().size)

        // reset 후 길이 체크
        itemRepository.reset()
        assertEquals(0, itemRepository.dataFlow.first().size)
    }
}