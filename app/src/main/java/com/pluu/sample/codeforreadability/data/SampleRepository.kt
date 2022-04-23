package com.pluu.sample.codeforreadability.data

import kotlinx.coroutines.flow.flow

class SampleRepository(
    private val sampleApi: SampleApi
) : SampleApi by sampleApi {
    fun sendLogFlow() = flow {
        emit(sampleApi.sendLog())
    }
}