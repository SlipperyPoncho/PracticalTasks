package com.artem.android.task1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyService: Service() {

    private lateinit var executor: ExecutorService

    override fun onCreate() {
        super.onCreate()
        executor = Executors.newSingleThreadExecutor()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        executor.execute {
            Thread.sleep(5000)
            val resultCategories = readJSONFromAssets(applicationContext, "categories.json")
            val resultEvents = readJSONFromAssets(applicationContext, "events.json")
            val newIntent = Intent(MainActivity.BROADCAST_ACTION)
                .putExtra(MainActivity.PARAM_RESULT_CATEGORIES, resultCategories)
                .putExtra(MainActivity.PARAM_RESULT_EVENTS, resultEvents)
            sendBroadcast(newIntent)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}