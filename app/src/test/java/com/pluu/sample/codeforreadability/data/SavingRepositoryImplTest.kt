package com.pluu.sample.codeforreadability.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.pluu.sample.codeforreadability.provider.providePreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SavingRepositoryImplTest {

    private lateinit var repository: SavingRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val preference = providePreference(context)
        preference.edit().clear().apply()

        repository = SavingRepositoryImpl(preference)
    }

    @Test
    fun testSaveAndFlow() = runTest {
        val defaultValue= repository.favoriteTextFlow.first()
        assertEquals("", defaultValue)

        repository.saveFavorite("A")
        val result = repository.favoriteTextFlow.first()
        assertEquals("A", result)
    }
}