package com.artem.android.core.domain.usecases

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.artem.android.core.domain.models.EventModel
import com.artem.android.core.domain.repositories.EventsRepository
import com.artem.android.core.domain.mapper.Mapper
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val mapper: Mapper
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(): MutableLiveData<List<EventModel>> {
        return eventsRepository.getEvents().map {
            mapper.mapEvents(it)
        } as MutableLiveData<List<EventModel>>
    }
}