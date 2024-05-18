package com.artem.android.task1.domain.repositories

import androidx.lifecycle.LiveData
import com.artem.android.task1.data.entities.Event

interface EventsRepository {
    suspend fun requestEvents(eventsFromAssets: String): List<Event>
    suspend fun insertEvents(events: List<Event>)
    suspend fun getEvents(): LiveData<List<Event>>
}