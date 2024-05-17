package com.artem.android.task1.data

import androidx.lifecycle.LiveData
import com.artem.android.task1.data.database.CharityDao
import com.artem.android.task1.domain.EventsRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val retrofitApi: RetrofitApi,
    private val charityDao: CharityDao
): EventsRepository {

    private suspend fun eventsFromJSON(jsonFromAssets: String) {
        val jsonArray: JsonArray = JsonParser.parseString(jsonFromAssets).asJsonArray
        val events: MutableList<Event> = mutableListOf()

        for (json in jsonArray) {
            val eventData = Gson().fromJson(json, Event::class.java)
            events.add(eventData)
        }
        charityDao.insertAllEvents(events)
    }

    override suspend fun getEvents(eventsFromAssets: String): LiveData<List<Event>> {
        try {
            val response = retrofitApi.getEvents()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    charityDao.insertAllEvents(data)
                }
            } else {
                eventsFromJSON(eventsFromAssets)
            }
        } catch (e: Exception) {
            eventsFromJSON(eventsFromAssets)
        }
        return charityDao.getEvents()
    }
}