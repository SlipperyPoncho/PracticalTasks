package com.artem.android.newsfeature.eventdetailfragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.artem.android.core.R

class MyWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    private lateinit var notificationManager: NotificationManager

    override fun doWork(): Result {
        notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val channelId = "notification_channel"
        val channel = NotificationChannel(
            channelId,
            "Notification Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val eventId = inputData.getString("uuid")
        val eventTitle = inputData.getString("title")
        val moneySum = inputData.getInt("sum", 0)

        val intent = Intent("com.artem.android.task1.TASK1")
            .putExtra("openFragment", "eventDetailFragment")
            .putExtra("eventId", eventId)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent = PendingIntent.getActivity(applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE)

        val snoozeIntent = Intent(applicationContext,
            NotificationBroadcastReceiver::class.java).apply {
            putExtra("eventId", eventId)
            putExtra("title", eventTitle)
            putExtra("channelId", channelId)
        }

        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(applicationContext, 0, snoozeIntent,
                PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(eventTitle)
            .setContentText("Спасибо, что пожертвовали $moneySum ₽! " +
                    "Будем очень признательны, если вы сможете пожертвовать еще больше")
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Напомнить позже", snoozePendingIntent)
            .build()

        notificationManager.notify(1, notification)

        return Result.success()
    }
}