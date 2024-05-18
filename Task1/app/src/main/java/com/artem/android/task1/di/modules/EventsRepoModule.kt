package com.artem.android.task1.di.modules

import com.artem.android.task1.data.repositories.EventsRepositoryImpl
import com.artem.android.task1.domain.repositories.EventsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class EventsRepoModule {
    @Binds
    abstract fun bindEventsRepo(eventsRepositoryImpl: EventsRepositoryImpl):
            EventsRepository
}