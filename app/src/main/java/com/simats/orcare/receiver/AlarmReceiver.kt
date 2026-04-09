package com.simats.orcare.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.simats.orcare.util.NotificationHelper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "ORCare Reminder"
        val message = intent.getStringExtra("message") ?: "Time for your oral care routine!"
        val id = intent.getIntExtra("id", 0)

        val notificationHelper = NotificationHelper(context)
        notificationHelper.showNotification(id, title, message)
    }
}
