package com.artem.android.task1.di

import com.artem.android.authfeature.AuthComponent
import com.artem.android.authfeature.AuthModule
import com.artem.android.helpfeature.HelpComponent
import com.artem.android.helpfeature.HelpModule
import com.artem.android.newsfeature.NewsComponent
import com.artem.android.newsfeature.NewsModule
import com.artem.android.profilefeature.ProfileComponent
import com.artem.android.profilefeature.ProfileModule
import com.artem.android.searchfeature.SearchComponent
import com.artem.android.searchfeature.SearchModule
import com.artem.android.splashfeature.SplashComponent
import com.artem.android.splashfeature.SplashModule
import com.artem.android.task1.MainActivity
import com.artem.android.task1.presentation.mainviewmodel.MainActivityViewModelFactory
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
    EventsRepoModule::class,
    NewsModule::class,
    HelpModule::class,
    AuthModule::class,
    ProfileModule::class,
    SplashModule::class,
    SearchModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun mainActivityViewModelFactory(): MainActivityViewModelFactory
    fun helpComponent(): HelpComponent.Factory
    fun newsComponent(): NewsComponent.Factory
    fun authComponent(): AuthComponent.Factory
    fun profileComponent(): ProfileComponent.Factory
    fun splashComponent(): SplashComponent.Factory
    fun searchComponent(): SearchComponent.Factory
}
