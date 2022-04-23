package com.pluu.sample.codeforreadability.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pluu.sample.codeforreadability.model.SampleItem
import com.pluu.sample.codeforreadability.provider.SampleItemGenerator
import com.pluu.sample.codeforreadability.provider.provideRepository
import kotlinx.coroutines.launch
import logcat.logcat

// FIXED 3. use ViewModel
class SearchViewModel(
    // FIXED 6. provide generator
    private val generator: SampleItemGenerator
) : ViewModel() {

    private val logRepository by lazy {
        provideRepository()
    }

    // FIXED 4. add LiveData
    private val _items = MutableLiveData<List<SampleItem>>()
    val items: LiveData<List<SampleItem>> get() = _items

    private val _messageEvent = MutableLiveData<String>()
    val messageEvent: LiveData<String> get() = _messageEvent

    private val cachedList = mutableListOf<SampleItem>()

    // FIXED 4. move generate
    fun generate() {
        // FIXED 6. use generator
        val item = generator.generate()

        if (cachedList.none { it.text == item.text }) {
            cachedList.add(item)
            _items.value = cachedList.sortedBy { it.text }
        } else {
            _messageEvent.value = "Duplicate item : ${item.text}"
        }
    }

    // FIXED 4. move reset
    fun reset() {
        sendLog()
        cachedList.clear()
        _items.value = emptyList()
    }

    // FIXED 3. move network
    private fun sendLog() {
        // Use OkHttp, Retrofit
        viewModelScope.launch {
            // Case 1.
            val result = logRepository.sendLog()
            logcat { result.toString() }

            // Case 2.
//            logRepository.sendLogFlow()
//                .collect { result ->
//                    logcat { result.toString() }
//                }
        }
    }
}