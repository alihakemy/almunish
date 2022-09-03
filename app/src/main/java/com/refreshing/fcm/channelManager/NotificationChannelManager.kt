package com.refreshing.fcm.channelManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.refreshing.R


class NotificationChannelObject {
    var channelName:String?=null
    var channelDescription:String?=null
    var channelId:String ?=null

}

object NotificationChannelManager {
    private val channelArray: ArrayList<NotificationChannelObject> = arrayListOf()

    init {



        val webView = NotificationChannelObject()
        webView.channelDescription = ChannelDescription.ViewProfile.channelDescription
        webView.channelId = ChannelId.ViewProduct.name
        webView.channelName = ChannelName.ViewProfile.channelName



        channelArray.add(webView)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initChannel(context: Context) {
        for (i in 0 until channelArray.size) {

                createNotificationChannel(context, channelArray[i])


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context, channel: NotificationChannelObject) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library



        val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(channel.channelId, channel.channelName, importance).apply {
                description = channel.channelDescription

            }
        // Register the channel with the system
        val notificationManager: android.app.NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.createNotificationChannel(channel)

    }


    /**
     * Get the setting a user has applied to the notification channel.
     * If the android API level is < 26, it will return true if all notification
     * are enabled in general, false otherwise.
     *
     * @return true if the channel is enabled, false otherwise
     */
    private fun isChannelEnabled(context: Context,channelId: String?): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            if (notificationManager == null) {
                true
            } else {

                val c: NotificationChannel = notificationManager.getNotificationChannel(channelId)
                val overallEnabled: Boolean = notificationManager.areNotificationsEnabled()
                overallEnabled && c != null && NotificationManager.IMPORTANCE_NONE !== c.importance
            }
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    }


}