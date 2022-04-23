package com.pluu.sample.codeforreadability.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pluu.sample.codeforreadability.data.ItemRepository
import com.pluu.sample.codeforreadability.data.SavingRepository
import com.pluu.sample.codeforreadability.model.GenerateItem
import com.pluu.sample.codeforreadability.model.SampleItem
import com.pluu.sample.codeforreadability.provider.provideRepository
import kotlinx.coroutines.launch
import logcat.logcat

// FIXED 3. use ViewModel
class SearchViewModel(
    // FIXED 6. provide generator
    private val itemRepository: ItemRepository,
    // FIXED 7. provide saver
    private val savingRepository: SavingRepository
) : ViewModel() {

    private val logRepository by lazy {
        provideRepository()
    }

    // FIXED 4. add LiveData
    private val _items = MutableLiveData<List<SampleItem>>()
    val items: LiveData<List<SampleItem>> get() = _items

    private val _messageEvent = MutableLiveData<String>()
    val messageEvent: LiveData<String> get() = _messageEvent

    // FIXED 4. move generate
    fun generate() {
        // FIXED 9. use item repository
        itemRepository.generate()
            .onSuccess {
                refresh()
            }
            .onFailure {
                _messageEvent.value = it.message.orEmpty()
            }
    }

    // FIXED 4. move reset
    fun reset() {
        sendLog()
        itemRepository.reset()
        refresh()
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

    // FIXED 7. update favorite
    private fun updateFavorite(text: String) {
        savingRepository.saveFavorite(text)
        refresh()
    }

    // FIXED 9. modify refresh
    private fun refresh() {
        val savingText = savingRepository.getFavorite()
        _items.value = itemRepository.data.map { item ->
            item.toUiModel(
                isFavorite = item.text == savingText,
                onFavorite = ::updateFavorite
            )
        }.sortedBy { it.text }
    }
}

private fun GenerateItem.toUiModel(
    isFavorite: Boolean,
    onFavorite: (String) -> Unit
): SampleItem = SampleItem(
    text = text,
    bgColor = bgColor,
    isFavorite = isFavorite,
    onFavorite = onFavorite
)
