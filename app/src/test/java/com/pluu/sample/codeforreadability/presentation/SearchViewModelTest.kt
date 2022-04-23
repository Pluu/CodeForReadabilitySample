package com.pluu.sample.codeforreadability.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pluu.sample.codeforreadability.utils.getOrAwaitValue
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// FIXED 5. Add ViewModel Test
internal class SearchViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel()
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