package com.pluu.sample.codeforreadability.provider

import android.content.Context
import android.content.SharedPreferences
import com.pluu.sample.codeforreadability.data.SampleApi
import com.pluu.sample.codeforreadability.data.SampleRepository
import com.pluu.sample.codeforreadability.data.SampleRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun provideRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun provideRepository(): SampleRepository = SampleRepositoryImpl(provideApi())

private fun provideApi(): SampleApi = provideRetrofit()
    .create(SampleApi::class.java)

fun providePreference(context: Context): SharedPreferences =
    context.getSharedPreferences("sample", Context.MODE_PRIVATE)