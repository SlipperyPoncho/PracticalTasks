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
import com.artem.android.task1.domain.DatabaseRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class CharitySharedViewModel(private val mapper: Mapper = Mapper()): ViewModel() {

    private val charityRepository = DatabaseRepository.get()

    private val _categories: MutableLiveData<List<CategoryModel>> = charityRepository.getCategories()

    val categories: LiveData<List<CategoryModel>>
        get() = _categories

    private val _news: MutableLiveData<List<EventModel>> = charityRepository.getEvents()
    val newsEvents: LiveData<List<EventModel>>
        get() = _news

    private var events: List<EventModel> = emptyList()

    private val _searchEvents: MutableLiveData<List<EventModel>> = charityRepository.getEvents()
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

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: DataApi = retrofit.create(DataApi::class.java)
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
    private fun categoriesFromJSON(jsonFromAssets: String) {
        Observable.create<List<Category>> {
            val jsonArray: JsonArray = JsonParser.parseString(jsonFromAssets).asJsonArray
            val categories: MutableList<Category> = mutableListOf()

            for (json in jsonArray) {
                val categoryData = Gson().fromJson(json, Category::class.java)
                categories.add(categoryData)
            }
            it.onNext(categories)
        }.subscribeOn(Schedulers.io())
            .doOnNext {
                Log.i("Test", Thread.currentThread().name)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.i("Test", Thread.currentThread().name)
            }
            .subscribe {
                viewModelScope.launch {
                    charityRepository.insertAllCategories(it)
                }
            }
    }

    @SuppressLint("CheckResult")
    @Suppress("kotlin:S6310")
    private fun categoriesFromServer(categoriesFromAssets: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                try {
                    val response = service.getCategories()
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            charityRepository.insertAllCategories(data)
                        }
                    } else {
                        errorLiveData.postValue(response.message())
                        categoriesFromJSON(categoriesFromAssets)
                    }
                } catch (e: Exception) {
                    errorLiveData.postValue(e.message)
                }
            }
        }
    }

    private fun eventsFromJSON(jsonFromAssets: String) {
        val jsonArray: JsonArray = JsonParser.parseString(jsonFromAssets).asJsonArray
        val events: MutableList<Event> = mutableListOf()

        for (json in jsonArray) {
            val eventData = Gson().fromJson(json, Event::class.java)
            events.add(eventData)
        }

        viewModelScope.launch {
            charityRepository.insertAllEvents(events)
        }
        this.events = mapper.mapEvents(events)
        setValueToFlow(_unreadNewsCounter, this.events.size)
    }

    @SuppressLint("CheckResult")
    @Suppress("kotlin:S6310")
    private fun eventsFromServer(eventsFromAssets: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {
                try {
                    val response = service.getEvents()
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            charityRepository.insertAllEvents(data)
                            this@CharitySharedViewModel.events = mapper.mapEvents(data)
                            setValueToFlow(_unreadNewsCounter, this@CharitySharedViewModel.events.size)
                        }
                    } else {
                        errorLiveData.postValue(response.message())
                        eventsFromJSON(eventsFromAssets)
                    }
                } catch (e: Exception) {
                    errorLiveData.postValue(e.message)
                }
            }
        }
    }

    fun findEventById(eventId: UUID): EventModel? {
        return newsEvents.value?.let {
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
                it
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

    companion object {
        const val BASE_API_URL = "https://f9c564cb-ca58-49fe-8d76-f83ecf91f86c.mock.pstmn.io"
    }
}