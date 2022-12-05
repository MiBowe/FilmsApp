package com.example.sandbox.MainActivity_Fragments.data.api.PostRequest

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sandbox.MainActivity_Fragments.presentation.MainActivity
import com.example.sandbox.MainActivity_Fragments.presentation.MainActivity.Companion.FILM_ID
import com.example.sandbox.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val filmID = intent?.getIntExtra(FILM_ID, 0)
        val stringID = filmID.toString()

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("detailsFragment", "DetailsFragment")
        intent.putExtra(FILM_ID, stringID)
        Log.d("Принимаем ID фильма", "------------------------------------------------------------------------------> $stringID")

        val pendingIntent = PendingIntent.getActivity(context, filmID ?: ID_NOTIFICATION, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context!!, ID_CHANNEL)
            .setSmallIcon(R.drawable.ic_heart_filled)
            .setContentTitle("Alarm FCM notification")
            .setContentText("Вы собирались посмотреть фильм")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        Log.d("notification", "onReceive: Here is notification")

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(filmID ?: ID_NOTIFICATION, notification)

        Log.d("Receiver____________________________________________________________________>", "$filmID")

        Log.d("NotificationManager", "Notify был выполнен $notificationManager")
    }

    companion object {
        const val ID_CHANNEL = "Local notification"
        const val ID_NOTIFICATION = 1
    }

}

