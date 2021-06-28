package com.example.leitner.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.leitner.MainActivity
import com.example.leitner.R

//import com.example.android.eggtimernotifications.R
//import com.example.android.eggtimernotifications.receiver.SnoozeReceiver

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

//
//    val eggImage = BitmapFactory.decodeResource(
//        applicationContext.resources,
//        R.drawable.cooked_egg
//    )
//    val bigPicStyle = NotificationCompat.BigPictureStyle()
//        .bigPicture(eggImage)
//        .bigLargeIcon(null)
//
//
//    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
//    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
//        applicationContext,
//        REQUEST_CODE,
//        snoozeIntent,
//        FLAGS)

    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_check)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

//        .setStyle(bigPicStyle)
//        .setLargeIcon(eggImage)

//        .addAction(
//            R.drawable.egg_icon,
//            applicationContext.getString(R.string.snooze),
//            snoozePendingIntent
//        )

        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setNumber(0)
    notify(NOTIFICATION_ID, builder.build())
}

// TODO: Step 1.14 Cancel all notifications
/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
