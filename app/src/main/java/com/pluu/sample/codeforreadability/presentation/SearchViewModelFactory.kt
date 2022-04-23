package com.pluu.sample.codeforreadability.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pluu.sample.codeforreadability.data.ItemRepositoryImpl
import com.pluu.sample.codeforreadability.data.SampleRepositoryImpl
import com.pluu.sample.codeforreadability.data.SavingRepositoryImpl
import com.pluu.sample.codeforreadability.provider.RandomGeneratorImpl
import com.pluu.sample.codeforreadability.provider.provideRepository

class SearchViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            itemRepository = ItemRepositoryImpl(randomGenerator = RandomGeneratorImpl()),
            savingRepository = SavingRepositoryImpl(context),
            logRepository = SampleRepositoryImpl(provideRepository())
        ) as T
    }
}