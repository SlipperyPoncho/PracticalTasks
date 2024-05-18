package com.artem.android.task1.di

import com.artem.android.task1.presentation.helpfragment.viewmodel.HelpFragmentViewModelFactory
import com.artem.android.task1.MainActivity
import com.artem.android.task1.presentation.mainviewmodel.MainActivityViewModelFactory
import com.artem.android.task1.presentation.newsfragment.viewmodel.NewsFragmentViewModelFactory
import com.artem.android.task1.presentation.searchfragment.viewmodel.SearchFragmentViewModelFactory
import com.artem.android.task1.di.modules.CategoriesRepoModule
import com.artem.android.task1.di.modules.ContextModule
import com.artem.android.task1.di.modules.DatabaseModule
import com.artem.android.task1.di.modules.EventsRepoModule
import com.artem.android.task1.di.modules.NetworkModule
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
    fun mainActivityViewModelFactory(): MainActivityViewModelFactory
    fun helpFragmentViewModelFactory(): HelpFragmentViewModelFactory
    fun newsFragmentViewModelFactory(): NewsFragmentViewModelFactory
    fun searchFragmentViewModelFactory(): SearchFragmentViewModelFactory
}
