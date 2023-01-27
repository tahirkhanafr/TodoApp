package com.example.todolist.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todolist.R
import com.example.todolist.views.HomeFragment

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context,HomeFragment::class.java)
        intent!!.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         val pendingIntent = PendingIntent.getActivity(context,0,i,0)

        val builder=NotificationCompat.Builder(context!!,"IF")
            .setSmallIcon(R.drawable.notifications_24)
            .setContentTitle("To-Do List")
            .setContentText("Checking")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
        val notificationManager=NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build())

    }
}