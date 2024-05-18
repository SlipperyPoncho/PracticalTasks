package com.artem.android.task1.domain.usecases

import com.artem.android.task1.data.entities.Event
import com.artem.android.task1.domain.repositories.EventsRepository
import javax.inject.Inject

class InsertEventsUseCase @Inject constructor(
    private val eventsRepository: EventsRepository
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(events: List<Event>) {
        eventsRepository.insertEvents(events)
    }
}