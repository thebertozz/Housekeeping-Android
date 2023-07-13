package it.thebertozz.android.housekeeping.managers

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import it.thebertozz.android.housekeeping.Constants
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.notifications.NotificationReceiver
import java.util.Calendar

object NotificationsManager {

    val TAG = "NotificationsManager"

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context) {
        val name = "Avviso di scadenza"
        val description = "Canale per notificare la scadenza degli alimenti inseriti"
        val priority = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(Constants.notificationsChannel, name, priority)
        channel.description = description
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        manager?.createNotificationChannel(channel)
    }

    fun scheduleNotification(context: Context, itemName: String, itemNotificationId: Int, calendar: Calendar) {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("itemName", itemName)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            itemNotificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager?
        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        Toast.makeText(context, R.string.notification_set, Toast.LENGTH_LONG).show()
    }

    fun cancelNotification(context: Context, itemName: String, itemNotificationCode: Int) {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("itemName", itemName)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            itemNotificationCode,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager?
        alarmManager?.cancel(pendingIntent)
        Toast.makeText(context, R.string.notification_deleted, Toast.LENGTH_LONG).show()
    }
}