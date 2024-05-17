package com.artem.android.task1.domain

import androidx.lifecycle.LiveData
import com.artem.android.task1.data.Event

interface EventsRepository {
    suspend fun getEvents(eventsFromAssets: String): LiveData<List<Event>>
}