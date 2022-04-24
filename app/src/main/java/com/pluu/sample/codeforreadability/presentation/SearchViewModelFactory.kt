package com.pluu.sample.codeforreadability.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pluu.sample.codeforreadability.data.ItemRepositoryImpl
import com.pluu.sample.codeforreadability.data.SampleRepositoryImpl
import com.pluu.sample.codeforreadability.data.SavingRepositoryImpl
import com.pluu.sample.codeforreadability.provider.RandomGeneratorImpl
import com.pluu.sample.codeforreadability.provider.providePreference
import com.pluu.sample.codeforreadability.provider.provideRepository
import kotlinx.coroutines.Dispatchers

class SearchViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(
            itemRepository = ItemRepositoryImpl(
                randomGenerator = RandomGeneratorImpl()
            ),
            savingRepository = SavingRepositoryImpl(
                preferences = providePreference(context)
            ),
            logRepository = SampleRepositoryImpl(
                sampleApi = provideRepository()
            ),
            dispatcher = Dispatchers.Default
        ) as T
    }
}