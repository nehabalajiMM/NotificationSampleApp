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
    private val channelId = "notifications_channel"
    private val title = "Test notification"
    private val description = "This is a sample notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        button.setOnClickListener {
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
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setStyle(Notification.BigTextStyle().bigText(getString(R.string.sample_long_text)))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            } else {
                builder = Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                    .setStyle(Notification.BigTextStyle().bigText(getString(R.string.sample_long_text)))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            notificationManager.notify(1234, builder.build())
        }
    }
}
