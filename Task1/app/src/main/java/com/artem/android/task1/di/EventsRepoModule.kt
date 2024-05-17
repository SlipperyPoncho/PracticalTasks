package com.artem.android.task1.di

import com.artem.android.task1.data.EventsRepositoryImpl
import com.artem.android.task1.domain.EventsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class EventsRepoModule {
    @Binds
    abstract fun bindEventsRepo(eventsRepositoryImpl: EventsRepositoryImpl):
            EventsRepository
}