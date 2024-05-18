package com.artem.android.task1.data.repositories

import androidx.lifecycle.LiveData
import com.artem.android.task1.data.RetrofitApi
import com.artem.android.task1.data.database.CharityDao
import com.artem.android.task1.data.entities.Event
import com.artem.android.task1.domain.repositories.EventsRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val retrofitApi: RetrofitApi,
    private val charityDao: CharityDao
): EventsRepository {

    private fun eventsFromJSON(jsonFromAssets: String): List<Event> {
        val jsonArray: JsonArray = JsonParser.parseString(jsonFromAssets).asJsonArray
        val events: MutableList<Event> = mutableListOf()

        for (json in jsonArray) {
            val eventData = Gson().fromJson(json, Event::class.java)
            events.add(eventData)
        }
        return events
    }

    override suspend fun requestEvents(eventsFromAssets: String): List<Event> {
        try {
            val response = retrofitApi.getEvents()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    return data
                }
            } else {
                return eventsFromJSON(eventsFromAssets)
            }
        } catch (e: Exception) {
            return eventsFromJSON(eventsFromAssets)
        }
        return emptyList()
    }

    override suspend fun insertEvents(events: List<Event>) {
        charityDao.insertAllEvents(events)
    }

    override suspend fun getEvents(): LiveData<List<Event>> {
        return charityDao.getEvents()
    }
}