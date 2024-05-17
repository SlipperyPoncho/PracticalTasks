package com.artem.android.task1

import android.app.Application
import com.artem.android.task1.di.ContextModule
import com.artem.android.task1.di.DaggerAppComponent
import com.artem.android.task1.di.DatabaseModule
import com.artem.android.task1.di.NetworkModule

class App: Application() {
    val appComponent = DaggerAppComponent.builder()
        .contextModule(ContextModule(this))
        .databaseModule(DatabaseModule())
        .networkModule(NetworkModule())
        .build()
}