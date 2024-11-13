package com.example.servicefirsttry

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the EditText and Button by ID
        val editTextInput: EditText = findViewById(R.id.editTextInput)
        val startServiceButton: Button = findViewById(R.id.startServiceButton)


        // Set a click listener on the button
        startServiceButton.setOnClickListener {
            // Get the text from the EditText
            val inputText = editTextInput.text.toString()

            // Ensure that the input is a valid number
            val countdownTime = if (inputText.isNotEmpty()) {
                inputText.toIntOrNull() ?: 10  // Default to 10 seconds if invalid input
            } else {
                10  // Default to 10 seconds if no input
            }

            // If the countdown time is valid, start the service
            if (countdownTime > 0) {
                val serviceIntent = Intent(this, MyService::class.java)
                serviceIntent.putExtra("countdownTime", countdownTime)
                startService(serviceIntent)  // Start the service
            } else {
                Toast.makeText(this, "Please enter a valid number greater than 0", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

