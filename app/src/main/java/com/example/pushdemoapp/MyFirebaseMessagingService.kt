package com.example.pushdemoapp  // replace with your actual package name

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "Message received from: ${remoteMessage.from}")
        Log.d("FCM", "Notification body: ${remoteMessage.notification?.body}")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        // Send this token to your server if needed
    }
}
