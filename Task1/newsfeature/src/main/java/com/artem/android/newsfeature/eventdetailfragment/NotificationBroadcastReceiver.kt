package com.artem.android.newsfeature.eventdetailfragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class NotificationBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val eventId = intent?.getStringExtra("eventId")
        val eventTitle = intent?.getStringExtra("title")
        val channelId = intent?.getStringExtra("channelId")

        val data = Data.Builder()
            .putString("uuid", eventId.toString())
            .putString("title", eventTitle)
            .putString("channelId", channelId)
            .build()

        val remindLaterWork = OneTimeWorkRequest.Builder(MyRemindWorker::class.java)
            .setInitialDelay(10, TimeUnit.SECONDS) // Set delay for 15 minutes
            .setInputData(data)
            .build()
        if (context != null) {
            WorkManager.getInstance(context).enqueueUniqueWork(
                "remind_later_work",
                ExistingWorkPolicy.REPLACE,
                remindLaterWork
            )
        }
    }
}