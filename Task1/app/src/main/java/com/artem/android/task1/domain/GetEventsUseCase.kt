package com.artem.android.task1.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val mapper: Mapper
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(eventsFromAssets: String): MutableLiveData<List<EventModel>> {
        return eventsRepository.getEvents(eventsFromAssets).map {
            mapper.mapEvents(it)
        } as MutableLiveData<List<EventModel>>
    }
}