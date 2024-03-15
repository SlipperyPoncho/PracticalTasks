package com.artem.android.task1

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.util.UUID

class CharitySharedViewModel: ViewModel() {

    private val _categories: MutableLiveData<List<CategoryModel>> = MutableLiveData()
    val categories: LiveData<List<CategoryModel>>
        get() = _categories

    private val _events: MutableLiveData<List<Event>> = MutableLiveData()
    val currentEvents: LiveData<List<Event>>
        get() = _events

    private var events: List<Event> = emptyList()

    fun initializeData(context: Context) {
        categoriesFromJSON(context)
        eventsFromJSON(context)
    }

    private fun categoriesFromJSON(context: Context) {
        val jsonString = readJSONFromAssets(context, "categories.json")
        val jsonArray: JsonArray = JsonParser.parseString(jsonString).asJsonArray
        val categories: MutableList<Category> = mutableListOf()

        for (json in jsonArray) {
            val categoryData = Gson().fromJson(json, Category::class.java)
            categories.add(categoryData)
        }

        _categories.value = categories.map {
            CategoryModel(
                title = it.title,
                imageResId = it.imageResId,
                categoryType = it.categoryType
            )
        }
    }

    private fun eventsFromJSON(context: Context) {
        val jsonString = readJSONFromAssets(context, "events.json")
        val jsonArray: JsonArray = JsonParser.parseString(jsonString).asJsonArray
        val events: MutableList<Event> = mutableListOf()

        for (json in jsonArray) {
            val eventData = Gson().fromJson(json, Event::class.java)
            events.add(eventData)
        }

        _events.value = events
        this.events = events
    }

    fun findEventById(eventId: UUID): Event? {
        return currentEvents.value?.let {
            it.firstOrNull { event ->
                event.id == eventId
            }
        }
    }

    fun updateEvents(categoryTypes: List<CategoryType>) {
        _events.value = events.filter { it.categories.containsAll(categoryTypes) }
        _categories.value = _categories.value?.map {
            if (categoryTypes.contains(it.categoryType)) {
                it.copy(isChecked = true)
            }
            else {
                it
            }
        }
    }
}