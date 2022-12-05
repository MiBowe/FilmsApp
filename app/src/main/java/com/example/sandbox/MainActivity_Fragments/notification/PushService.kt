package com.example.sandbox.MainActivity_Fragments.notification

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushService : FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val intent = Intent(PUSH_FILTER)
        remoteMessage.data.forEach{ entity ->
            intent.putExtra(entity.key, entity.value)
        }
        sendBroadcast(intent)
    }

    companion object{
        const val PUSH_FILTER = "PUSH_EVENT"
        const val KEY_ACTION = "action"
        const val KEY_MESSAGE = "message"

        const val ACTION_SHOW = "show_action"
    }
}