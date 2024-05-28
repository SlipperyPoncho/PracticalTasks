package com.artem.android.core.domain.repositories

import androidx.lifecycle.LiveData
import com.artem.android.core.data.entities.Event

interface EventsRepository {
    suspend fun requestEvents(eventsFromAssets: String): List<Event>
    suspend fun insertEvents(events: List<Event>)
    suspend fun getEvents(): LiveData<List<Event>>
}