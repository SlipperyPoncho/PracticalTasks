package com.artem.android.core.domain.usecases

import com.artem.android.core.data.entities.Event
import com.artem.android.core.domain.repositories.EventsRepository
import javax.inject.Inject

class RequestEventsUseCase @Inject constructor(
    private val eventsRepository: EventsRepository
) {
    @Suppress("kotlin:S6531")
    suspend operator fun invoke(eventsFromAssets: String): List<Event> {
        return eventsRepository.requestEvents(eventsFromAssets)
    }
}