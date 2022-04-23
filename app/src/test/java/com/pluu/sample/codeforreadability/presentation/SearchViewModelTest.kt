package com.pluu.sample.codeforreadability.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pluu.sample.codeforreadability.model.GenerateItem
import com.pluu.sample.codeforreadability.provider.GenerateItemGenerator
import com.pluu.sample.codeforreadability.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

// FIXED 5. Add ViewModel Test
@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    private lateinit var generateItemGenerator: GenerateItemGenerator

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        generateItemGenerator = mock {
            on { generate() } doReturn GenerateItem("", 0)
        }
        viewModel = SearchViewModel(
            generator = generateItemGenerator,
            savingRepository = mock()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun generate() {
        // when 1
        viewModel.generate()
        // then 1
        assertTrue(viewModel.items.getOrAwaitValue().isNotEmpty())

        // when 2
        viewModel.reset()
        // then 2
        assertTrue(viewModel.items.getOrAwaitValue().isEmpty())
    }
}