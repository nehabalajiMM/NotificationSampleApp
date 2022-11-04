package com.example.notificationsampleapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "notifications_channel_1"
    private val channelId2 = "notifications_channel_2"
    private val title = "Test notification"
    private val description = "This is a sample notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val largeTextNotificationButton = findViewById<Button>(R.id.button_notify_large_text)
        val imageNotificationButton = findViewById<Button>(R.id.button_notify_image)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        largeTextNotificationButton.setOnClickListener {
            val intent = Intent(this, LargeTextActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSmallIcon(R.drawable.sample_image)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.sample_image))
                    .setStyle(Notification.BigTextStyle().bigText(getString(R.string.sample_long_text)))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            } else {
                builder = Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSmallIcon(R.drawable.sample_image)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.sample_image))
                    .setStyle(Notification.BigTextStyle().bigText(getString(R.string.sample_long_text)))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            notificationManager.notify(1234, builder.build())
        }

        imageNotificationButton.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val sampleImage = BitmapFactory.decodeResource(resources, R.drawable.sample_image)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId2, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId2)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSmallIcon(R.drawable.sample_image)
                    .setLargeIcon(sampleImage)
                    .setStyle(Notification.BigPictureStyle().bigPicture(sampleImage))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            } else {
                builder = Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSmallIcon(R.drawable.sample_image)
                    .setLargeIcon(sampleImage)
                    .setStyle(Notification.BigPictureStyle().bigPicture(sampleImage))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            notificationManager.notify(1234, builder.build())
        }
    }
}
