package com.artem.android.task1.di

import com.artem.android.task1.MainActivity
import com.artem.android.task1.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,
    ContextModule::class,
    DatabaseModule::class,
    CategoriesRepoModule::class,
    EventsRepoModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun viewModelFactory(): ViewModelFactory
}
