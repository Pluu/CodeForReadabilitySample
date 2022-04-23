package com.pluu.sample.codeforreadability.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

interface SavingRepository {
    fun saveFavorite(text: String)

    fun getFavorite(): String
}

class SavingRepositoryImpl(
    context: Context
) : SavingRepository {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("sample", Context.MODE_PRIVATE)

    override fun saveFavorite(text: String) {
        preferences.edit {
            putString("KEY", text)
        }
    }

    override fun getFavorite(): String {
        return preferences.getString("KEY", null).orEmpty()
    }
}