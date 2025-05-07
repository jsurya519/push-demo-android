package com.example.pushdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.pushdemoapp.ui.theme.PushDemoAppTheme
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log

class MainActivity : ComponentActivity() {
    // Declare a mutable state to store the token
    private val fcmToken: MutableState<String> = mutableStateOf("Loading...")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Fetch the FCM token and update the UI
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM", "FCM Token: $token")
                // Store the token to be shown in the UI
                fcmToken.value = token ?: "Token not found"
            } else {
                Log.e("FCM", "Fetching FCM token failed", task.exception)
                fcmToken.value = "Failed to fetch token"
            }
        }

        setContent {
            PushDemoAppTheme {
                // Display the token in the UI
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplayFCMToken(
                        token = fcmToken.value,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayFCMToken(token: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "FCM Token:",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
        )

        // OutlinedTextField to show the token
        OutlinedTextField(
            value = token,
            onValueChange = {},  // No action required for a read-only field
            readOnly = true,  // Make it read-only so that the user can't modify it
            textStyle = TextStyle(fontSize = 16.sp),  // Use textStyle instead of style
            modifier = Modifier.fillMaxSize()  // Adjust size if needed
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFCMToken() {
    PushDemoAppTheme {
        DisplayFCMToken(token = "Sample Token")
    }
}
