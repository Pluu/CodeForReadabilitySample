package com.pluu.sample.codeforreadability.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.pluu.sample.codeforreadability.utils.keyFlow
import kotlinx.coroutines.flow.*

interface SavingRepository {
    // FIXED 14. modify data type
    val favoriteTextFlow: Flow<String>

    fun saveFavorite(text: String)
}

class SavingRepositoryImpl(
    private val preferences: SharedPreferences
) : SavingRepository {

    private val key = "KEY"

    // FIXED 14. modify data type
    override val favoriteTextFlow: Flow<String> = preferences.keyFlow
        .filter { it == key || it == null }
        .onStart { emit("Start") }
        .map { getFavorite() }
        .conflate()

    override fun saveFavorite(text: String) {
        preferences.edit {
            putString(key, text)
        }
    }

    private fun getFavorite(): String {
        return preferences.getString(key, null).orEmpty()
    }
}