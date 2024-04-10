package com.artem.android.task1

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.UUID

class CharitySharedViewModel: ViewModel() {

    private val _categories: MutableLiveData<List<CategoryModel>> = MutableLiveData()
    val categories: LiveData<List<CategoryModel>>
        get() = _categories

    private val _searchEvents: MutableLiveData<List<EventModel>> = MutableLiveData()
    private val _news: MutableLiveData<List<EventModel>> = MutableLiveData()

    val newsEvents: LiveData<List<EventModel>>
        get() = _news
    val searchEvents: LiveData<List<EventModel>>
        get() = _searchEvents

    private var events: List<EventModel> = emptyList()

    val unreadNewsSubject: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)

    fun initializeData(categoriesFromAssets: String, eventsFromAssets: String) {
        categoriesFromJSON(categoriesFromAssets)
        eventsFromJSON(eventsFromAssets)
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
            _categories.postValue(it.map { category ->
                CategoryModel(
                    title = category.title,
                    image = category.image,
                    categoryType = category.categoryType
                )
            })
        }
    }

    private fun eventsFromJSON(jsonFromAssets: String) {
        val jsonArray: JsonArray = JsonParser.parseString(jsonFromAssets).asJsonArray
        val events: MutableList<Event> = mutableListOf()

        for (json in jsonArray) {
            val eventData = Gson().fromJson(json, Event::class.java)
            events.add(eventData)
        }

        val mappedEvents = mapEvents(events)
        _searchEvents.postValue(mappedEvents)
        _news.postValue(mappedEvents)
        this.events = mappedEvents
        unreadNewsSubject.onNext(this.events.size)
    }

    private fun mapEvents(events: List<Event>): List<EventModel> {
        return events.map {
            EventModel(
                id = it.id,
                title = it.title,
                images = it.images,
                eventDetails = it.eventDetails,
                eventDateStart = it.eventDateStart,
                eventDateFinish = it.eventDateFinish,
                sponsor = it.sponsor,
                address = it.address,
                phoneNumbers = it.phoneNumbers,
                detailsText1 = it.detailsText1,
                detailsText2 = it.detailsText2,
                categories = it.categories
            )
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
            if (!i.isRead) unreadFilteredNews += 1
        }
        unreadNewsSubject.onNext(unreadFilteredNews)
    }

    fun updateUnreadNewsCounter(eventId: UUID) {
        _news.value = _news.value?.map {
            if (it.id == eventId && !it.isRead) {
                unreadNewsSubject.onNext(unreadNewsSubject.value?.minus(1) ?: 0)
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

    fun updateSearchEvents(query: String) {
        _searchEvents.value = events.filter { it.title.contains(query, true) }
    }
}