package com.artem.android.newsfeature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.android.core.data.CategoryType
import com.artem.android.core.domain.models.CategoryModel
import com.artem.android.core.domain.models.EventModel
import com.artem.android.core.domain.usecases.GetCategoriesUseCase
import com.artem.android.core.domain.usecases.GetEventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class NewsFragmentViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private var _categories: MutableLiveData<List<CategoryModel>> = MutableLiveData(emptyList())
    val categories: LiveData<List<CategoryModel>>
        get() = _categories

    private var _news: MutableLiveData<List<EventModel>> = MutableLiveData(emptyList())
    val newsEvents: LiveData<List<EventModel>>
        get() = _news

    private var events: List<EventModel> = emptyList()

    private val _unreadNewsCounter = MutableStateFlow(0)
    val unreadNewsCounter = _unreadNewsCounter.asStateFlow()

    private fun setValueToFlow(stateFlow: MutableStateFlow<Int>, value: Int) {
        viewModelScope.launch {
            stateFlow.update { value }
        }
    }

    private fun decrementCounter(stateFlow: MutableStateFlow<Int>) {
        viewModelScope.launch {
            stateFlow.update { count -> count - 1 }
        }
    }

    @Suppress("kotlin:S6310")
    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories = getCategoriesUseCase()
        }
    }

    @Suppress("kotlin:S6310")
    fun getEvents(onNavigateToDetails: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _news = getEventsUseCase()
            onNavigateToDetails()
        }
    }

    fun setEvents(list: List<EventModel>) {
        if (events.isEmpty()) {
            events = list
            setValueToFlow(_unreadNewsCounter, events.size)
        }
    }

    fun updateNews(categoryTypes: List<CategoryType>) {
        _news.value = events.filter { it.categories.containsAll(categoryTypes) }
        _categories.value = _categories.value?.map {
            if (categoryTypes.contains(it.categoryType)) {
                it.copy(isChecked = true)
            }
            else {
                it.copy(isChecked = false)
            }
        }

        var unreadFilteredNews = 0
        for (i in _news.value!!) {
            if (!i.isRead) {
                unreadFilteredNews += 1
            }
        }
        setValueToFlow(_unreadNewsCounter, unreadFilteredNews)
    }

    fun findEventById(eventId: UUID): EventModel? {
        return _news.value?.let {
            it.firstOrNull { event ->
                event.id == eventId
            }
        }
    }

    fun updateUnreadNewsCounter(eventId: UUID) {
        _news.value = _news.value?.map {
            if (it.id == eventId && !it.isRead) {
                decrementCounter(_unreadNewsCounter)
                it.copy(isRead = true)
            } else {
                it
            }
        }
        for (i in events) {
            for (j in _news.value!!) {
                if (i.id == j.id) {
                    i.isRead = j.isRead
                }
            }
        }
    }
}