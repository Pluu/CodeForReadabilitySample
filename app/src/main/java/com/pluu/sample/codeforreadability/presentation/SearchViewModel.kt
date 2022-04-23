package com.pluu.sample.codeforreadability.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pluu.sample.codeforreadability.provider.provideRepository
import kotlinx.coroutines.launch
import logcat.logcat

// FIXED 3. use ViewModel
class SearchViewModel : ViewModel() {

    private val logRepository by lazy {
        provideRepository()
    }

    // FIXED 3. move network
    fun sendLog() {
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