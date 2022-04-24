package com.pluu.sample.codeforreadability.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pluu.sample.codeforreadability.data.ItemRepository
import com.pluu.sample.codeforreadability.data.SavingRepository
import com.pluu.sample.codeforreadability.model.ColorValue
import com.pluu.sample.codeforreadability.model.GenerateItem
import com.pluu.sample.codeforreadability.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

// FIXED 5. Add ViewModel Test
@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    private val itemRepository: ItemRepository = mock()

    @Mock
    private val savingRepository: SavingRepository = mock()

    private val dispatcher = UnconfinedTestDispatcher()

    private val itemFlow: MutableStateFlow<List<GenerateItem>> = MutableStateFlow(emptyList())

    private val favoriteTextFlow: MutableStateFlow<String> = MutableStateFlow("")

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        whenever(itemRepository.dataFlow)
            .thenReturn(itemFlow)
        whenever(savingRepository.favoriteTextFlow)
            .thenReturn(favoriteTextFlow)

        viewModel = SearchViewModel(
            itemRepository = itemRepository,
            savingRepository = savingRepository,
            logRepository = mock(),
            dispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun generate() = runTest {
        // 최초 크기 체크
        assertTrue(viewModel.items.getOrAwaitValue().isEmpty())

        // 즐겨찾기 변경
        favoriteTextFlow.tryEmit("A")
        assertTrue(viewModel.items.getOrAwaitValue().isEmpty())

        // when 1
        val resultForWhen1 = GenerateItem("A", ColorValue(0))
        whenever(itemRepository.generate())
            .thenReturn(Result.success(resultForWhen1))

        viewModel.generate()
        itemFlow.tryEmit(listOf(resultForWhen1))

        // then 1
        val list1 = viewModel.items.getOrAwaitValue()
        assertTrue(list1.isNotEmpty())
        assertTrue(list1.first().isFavorite)

        // when 2
        viewModel.reset()
        itemFlow.tryEmit(emptyList())

        // then 2
        assertTrue(viewModel.items.getOrAwaitValue().isEmpty())

        // when 3
        val resultForWhen2 = GenerateItem("B", ColorValue(0))
        whenever(itemRepository.generate())
            .thenReturn(Result.success(resultForWhen2))

        viewModel.generate()
        itemFlow.tryEmit(listOf(resultForWhen2))

        // then 3
        val list2 = viewModel.items.getOrAwaitValue()
        assertTrue(list2.isNotEmpty())
        assertFalse(list2.first().isFavorite)
    }
}