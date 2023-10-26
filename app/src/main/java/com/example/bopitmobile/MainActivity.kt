package com.example.bopitmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.media.MediaPlayer;
import android.media.PlaybackParams

class MainActivity : AppCompatActivity() {

    lateinit var themeSoundPlayer : MediaPlayer
    private var playbackParams : PlaybackParams? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonOKGame = findViewById<Button>(R.id.PlayButton)

        val buttonOKAbout = findViewById<Button>(R.id.AboutButton)

        val buttonOKSetting = findViewById<Button>(R.id.SettingButton)

        themeSoundPlayer = MediaPlayer.create(this, R.raw.theme)
        themeSoundPlayer.isLooping = true

        themeSoundPlayer.start()


        playbackParams = themeSoundPlayer.playbackParams


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

    override fun onResume() {
        super.onResume()
        themeSoundPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        themeSoundPlayer.pause()
    }
}