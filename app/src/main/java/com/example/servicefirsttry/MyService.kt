package com.example.servicefirsttry

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*

// Service Class
class MyService : Service() {

    private var countdownJob: Job? = null  // Coroutine job for countdown

    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "Service Created")
        //Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show()
    }

    // implement onStartCommand
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Retrieve the countdown time passed from the Activity
        val countdownTime = intent?.getIntExtra("countdownTime", 10) ?: 10

        Log.d("MyService", "Starting countdown from $countdownTime seconds")

        // Start the countdown timer using Coroutines
        startCountdown(countdownTime)

        return START_STICKY  // Keeps the service running even if the system kills it
    }

    // Coroutine-based countdown logic
    private fun startCountdown(seconds: Int) {
        // Launch a coroutine on the IO dispatcher for background work
        countdownJob = CoroutineScope(Dispatchers.IO).launch {
            for (i in seconds downTo 1) {
                delay(1000)  // Wait for 1 second
                Log.d("MyService", "Countdown: $i seconds remaining")

            }

            // Once the countdown is finished, log it
            Log.d("MyService", "Countdown finished!")

            // Stop the service after countdown
            stopSelf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "Service Destroyed")
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show()

        // Cancel the countdown job if the service is destroyed
        countdownJob?.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null  // This is a started service, not a bound service.
    }
}

