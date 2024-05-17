package com.artem.android.task1

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.artem.android.task1.domain.CategoryModel
import com.artem.android.task1.domain.EventModel
import com.artem.android.task1.domain.GetCategoriesUseCase
import com.artem.android.task1.domain.GetEventsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class CharitySharedViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getEventsUseCase: GetEventsUseCase
): ViewModel() {

    private var _categories: MutableLiveData<List<CategoryModel>> = MutableLiveData(emptyList())

    val categories: LiveData<List<CategoryModel>>
        get() = _categories

    private var _news: MutableLiveData<List<EventModel>> = MutableLiveData(emptyList())
    val newsEvents: LiveData<List<EventModel>>
        get() = _news

    private var events: List<EventModel> = emptyList()

    private var _searchEvents: MutableLiveData<List<EventModel>> = MutableLiveData(emptyList())
    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: LiveData<List<EventModel>> = searchQuery
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            liveData {
                updateSearchEvents(query)
                emit(_searchEvents.value ?: emptyList())
            }.asFlow()
        }.asLiveData()

    private val _unreadNewsCounter = MutableStateFlow(0)
    val unreadNewsCounter = _unreadNewsCounter.asStateFlow()

    private val errorLiveData = MutableLiveData<String>()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        errorLiveData.value = e.message
    }

    private fun decrementCounter(stateFlow: MutableStateFlow<Int>) = viewModelScope.launch {
        stateFlow.update { count -> count - 1 }
    }

    private fun setValueToFlow(stateFlow: MutableStateFlow<Int>, value: Int) = viewModelScope.launch {
        stateFlow.update { value }
    }

    fun initializeData(categoriesFromAssets: String, eventsFromAssets: String) {
        categoriesFromServer(categoriesFromAssets)
        eventsFromServer(eventsFromAssets)
    }

    @SuppressLint("CheckResult")
    @Suppress("kotlin:S6310")
    private fun categoriesFromServer(categoriesFromAssets: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                try {
                    _categories = getCategoriesUseCase(categoriesFromAssets)
                    Log.i("Test_category", _categories.value.toString())
                } catch (e: Exception) {
                    errorLiveData.postValue(e.message)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    @Suppress("kotlin:S6310")
    private fun eventsFromServer(eventsFromAssets: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                try {
                    _news = getEventsUseCase(eventsFromAssets)
                    _searchEvents = _news
                } catch (e: Exception) {
                    errorLiveData.postValue(e.message)
                }
            }
        }
    }

    fun setEvents(list: List<EventModel>) {
        if (events.isEmpty()) {
            events = list
            setValueToFlow(_unreadNewsCounter, events.size)
        }
    }

    fun findEventById(eventId: UUID): EventModel? {
        return _news.value?.let {
            it.firstOrNull { event ->
                event.id == eventId
            }
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

    private fun updateSearchEvents(query: String) {
        _searchEvents.value = events.filter { it.title.contains(query, true) }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }
}