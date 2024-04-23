package com.artem.android.task1

import android.app.Application
import com.artem.android.task1.domain.DatabaseRepository

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseRepository.initialize(this)
    }
}