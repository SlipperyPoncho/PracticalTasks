package com.artem.android.task1

import android.app.Application
import com.artem.android.task1.di.AppComponent
import com.artem.android.task1.di.modules.ContextModule
import com.artem.android.task1.di.DaggerAppComponent
import com.artem.android.task1.di.modules.DatabaseModule
import com.artem.android.task1.di.modules.NetworkModule

class App: Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder()
        .contextModule(ContextModule(this))
        .databaseModule(DatabaseModule())
        .networkModule(NetworkModule())
        .build()
}