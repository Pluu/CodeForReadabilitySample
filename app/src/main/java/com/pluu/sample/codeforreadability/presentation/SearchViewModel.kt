package com.pluu.sample.codeforreadability.presentation

import androidx.lifecycle.*
import com.pluu.sample.codeforreadability.data.ItemRepository
import com.pluu.sample.codeforreadability.data.SampleRepository
import com.pluu.sample.codeforreadability.data.SavingRepository
import com.pluu.sample.codeforreadability.model.GenerateItem
import com.pluu.sample.codeforreadability.model.SampleItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import logcat.logcat

// FIXED 3. use ViewModel
class SearchViewModel(
    // FIXED 6. provide generator
    private val itemRepository: ItemRepository,
    // FIXED 7. provide saver
    private val savingRepository: SavingRepository,
    private val logRepository: SampleRepository,
    dispatcher: CoroutineDispatcher
) : ViewModel() {

    // FIXED 4. add LiveData
    // FIXED 14. use flow combine
    val items: LiveData<List<SampleItem>> = savingRepository.favoriteTextFlow.combine(
        itemRepository.dataFlow
    ) { savingText, list ->
        list.map { item ->
            item.toUiModel(
                isFavorite = item.text == savingText,
                onFavorite = ::updateFavorite
            )
        }
    }.onStart {
        emit(emptyList())
    }.flowOn(dispatcher)
        .asLiveData()

    private val _messageEvent = MutableLiveData<String>()
    val messageEvent: LiveData<String> get() = _messageEvent

    // FIXED 4. move generate
    fun generate() {
        // FIXED 9. use item repository
        itemRepository.generate()
            .onFailure {
                _messageEvent.value = it.message.orEmpty()
            }
    }

    // FIXED 4. move reset
    fun reset() {
        sendLog()
        itemRepository.reset()
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
