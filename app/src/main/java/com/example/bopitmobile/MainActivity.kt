package com.example.bopitmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonOKGame = findViewById<Button>(R.id.PlayButton)

        val buttonOKAbout = findViewById<Button>(R.id.AboutButton)

        val buttonOKSetting = findViewById<Button>(R.id.SettingButton)


        buttonOKGame.setOnClickListener {
            val intentGame = Intent(this, GameActivity::class.java)
            startActivity(intentGame)
        }

        buttonOKAbout.setOnClickListener {
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        buttonOKSetting.setOnClickListener {
            val intentSetting = Intent(this, PreferencesActivity::class.java)
            startActivity(intentSetting)
        }
    }
}