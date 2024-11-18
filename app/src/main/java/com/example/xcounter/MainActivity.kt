package com.example.xcounter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            Box(modifier = Modifier.background(Color.White).fillMaxSize()) {
                TwitterCharacterCountUI(
                    onPostClick = { tweet ->
                        Thread {
                            TwitterApiHelper.postTweet(tweet) { success ->
                                runOnUiThread {
                                    if (success) {
                                        Toast.makeText(context, "Tweet posted successfully!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Failed to post tweet.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }.start()
                    }
                )
            }
        }
    }
}



