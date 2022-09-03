package com.refreshing.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.refreshing.R

object NotificationManager {


    /**
     * Show Notification without  any action or grouping or image or summary
     * only notification
     */
    fun showBasicNotificationChats(
        context: Context,
        CHANNEL_ID: String,
        notificationId: Int
        /**come From Server to remove notification if not needed */
        ,
        intent: Intent, title: String, content: String
    ) {


        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.l)
            .setSubText(title)
            .setContentText(content)

            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.MessagingStyle(title)
                    .addMessage(content, System.currentTimeMillis(), title)

            )



        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(
                notificationId
                /**notificationId*/
                , builder.build()
            )
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun cancelChannel(context: Context, channelId: String) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.deleteNotificationChannel(channelId)

    }


    fun cancelNotificationById(context: Context, notificationId: Int) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }


    fun cancelAllNotification(context: Context) {
        NotificationManagerCompat.from(context).cancelAll()
    }


}