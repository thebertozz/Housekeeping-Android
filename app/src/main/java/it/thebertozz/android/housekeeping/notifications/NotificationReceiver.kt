package it.thebertozz.android.housekeeping.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import it.thebertozz.android.housekeeping.Constants
import it.thebertozz.android.housekeeping.MainActivity
import it.thebertozz.android.housekeeping.R
import kotlin.random.Random

/** Classe che estende BroadcastReceiver per la gestione della notifica schedulata */

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("itemName").toString()
        val notificationBuilder =
            NotificationCompat.Builder(context, Constants.notificationsChannel)
                .setContentText(message + " " + context.getString(R.string.notification_description))
                .setContentTitle(context.resources.getString(R.string.notification_title))
                .setSmallIcon(R.drawable.ic_stat_info_outline).build()

        val contentIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        notificationBuilder.contentIntent = contentIntent

        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Random.nextInt(), notificationBuilder)
    }
}