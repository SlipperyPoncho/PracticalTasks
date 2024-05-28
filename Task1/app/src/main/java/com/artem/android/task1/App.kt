package com.artem.android.task1

import android.app.Application
import com.artem.android.authfeature.AuthComponent
import com.artem.android.authfeature.AuthComponentProvider
import com.artem.android.helpfeature.HelpComponent
import com.artem.android.helpfeature.HelpComponentProvider
import com.artem.android.newsfeature.NewsComponent
import com.artem.android.newsfeature.NewsComponentProvider
import com.artem.android.profilefeature.ProfileComponent
import com.artem.android.profilefeature.ProfileComponentProvider
import com.artem.android.searchfeature.SearchComponent
import com.artem.android.searchfeature.SearchComponentProvider
import com.artem.android.splashfeature.SplashComponent
import com.artem.android.splashfeature.SplashComponentProvider
import com.artem.android.task1.di.AppComponent
import com.artem.android.task1.di.modules.ContextModule
import com.artem.android.task1.di.DaggerAppComponent
import com.artem.android.task1.di.modules.DatabaseModule
import com.artem.android.task1.di.modules.NetworkModule

class App: Application(),
    NewsComponentProvider,
    HelpComponentProvider,
    AuthComponentProvider,
    ProfileComponentProvider,
    SplashComponentProvider,
    SearchComponentProvider {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .contextModule(ContextModule(this))
        .databaseModule(DatabaseModule())
        .networkModule(NetworkModule())
        .build()

    override fun provideNewsComponent(): NewsComponent {
        return appComponent.newsComponent().create()
    }

    override fun provideHelpComponent(): HelpComponent {
        return appComponent.helpComponent().create()
    }

    override fun provideAuthComponent(): AuthComponent {
        return appComponent.authComponent().create()
    }

    override fun provideProfileComponent(): ProfileComponent {
        return appComponent.profileComponent().create()
    }

    override fun provideSplashComponent(): SplashComponent {
        return appComponent.splashComponent().create()
    }

    override fun provideSearchComponent(): SearchComponent {
        return appComponent.searchComponent().create()
    }
}