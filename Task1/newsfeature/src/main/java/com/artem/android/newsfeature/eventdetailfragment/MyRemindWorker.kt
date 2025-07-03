package com.artem.android.newsfeature.eventdetailfragment

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.artem.android.core.R

class MyRemindWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    private lateinit var notificationManager: NotificationManager

    override fun doWork(): Result {

        notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val eventId = inputData.getString("eventId")
        val eventTitle = inputData.getString("title")
        val channelId = inputData.getString("channelId")

        val intent = Intent("com.artem.android.task1.TASK1")
            .putExtra("openFragment", "eventDetailFragment")
            .putExtra("eventId", eventId)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent = PendingIntent.getActivity(applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, channelId ?: "")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(eventTitle)
            .setContentText("Напоминаем, что мы будем очень признательны, " +
                    "если вы сможете пожертвовать еще больше")
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)

        return Result.success()
    }
}