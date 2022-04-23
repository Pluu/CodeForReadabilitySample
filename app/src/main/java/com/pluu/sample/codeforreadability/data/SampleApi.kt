package com.pluu.sample.codeforreadability.data

import retrofit2.http.GET

interface SampleApi {
    @GET("/users/google")
    suspend fun sendLog(): LogResult
}