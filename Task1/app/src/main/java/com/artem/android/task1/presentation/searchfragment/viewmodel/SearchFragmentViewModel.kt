package com.artem.android.task1.presentation.searchfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.artem.android.task1.domain.models.EventModel
import com.artem.android.task1.domain.usecases.GetEventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
): ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private var _searchEvents: MutableLiveData<List<EventModel>> = MutableLiveData(emptyList())
    val searchEvents: LiveData<List<EventModel>>
        get() = _searchEvents

    private var events: List<EventModel> = emptyList()

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

    @Suppress("kotlin:S6310")
    fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _searchEvents = getEventsUseCase()
        }
    }

    fun setEvents(list: List<EventModel>) {
        if (events.isEmpty()) {
            events = list
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    private fun updateSearchEvents(query: String) {
        _searchEvents.value = events.filter { it.title.contains(query, true) }
    }
}