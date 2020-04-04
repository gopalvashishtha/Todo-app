package com.example.todolist

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    var appDatabase: AppDatabase? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("WrongConstant")
    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let { initiateDatabase(it) }
        val notificationManager: NotificationManager =
            context?.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        var isFinished = intent?.getIntExtra("isFinished", 0) ?:0
        val dbId = intent?.getLongExtra("id", -1) ?: -1
        val title = intent?. getStringExtra("title") ?:" "
        val time = intent?.getStringExtra("date")?:""
        Log.d("Alarm Title", "title : $title")

        val icon = R.drawable.ic_launcher_background


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("Remainder", "My Notifications", NotificationManager.IMPORTANCE_MAX)
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description")
            notificationChannel.enableLights(true)
            notificationChannel.setLightColor(Color.RED)
            notificationChannel.enableVibration(true)
//            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationChannel.vibrationPattern
//            notificationChannel.getLockscreenVisibility()

            notificationManager.createNotificationChannel(notificationChannel)

        }

        val notification = NotificationCompat.Builder(context, "Remainder")
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(time)
            .setPriority(NotificationCompat.VISIBILITY_PUBLIC)
            .setColor(Color.RED)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(getNumber(), notification)


        appDatabase?.userDao()?.isShownUpdate(id = dbId, isShow = 1)


        val list = appDatabase?.userDao()?.get(dbId)

        Log.d("IsRead","isRead "+list?.isShow)

    }



    private fun initiateDatabase(context: Context) {
        if (appDatabase== null)
            appDatabase = AppDatabase.getDatabase(context)


    }
    fun getNumber(): Int = (Date().time / 1000L % Integer.MAX_VALUE).toInt()    // to show multiple number of notification , there is need of unique number
}
