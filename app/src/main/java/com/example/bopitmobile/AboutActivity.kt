package com.example.bopitmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val backOKAbout = findViewById<Button>(R.id.BackToMenuButton)

        backOKAbout.setOnClickListener {

            val intentBack = Intent(this, MainActivity::class.java)
            startActivity(intentBack)
        }
    }
}