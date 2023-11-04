package com.example.bopitmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.color.utilities.Score

class GameOverActivity : AppCompatActivity() {

    lateinit var ScoreText : TextView
    var score : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        ScoreText = findViewById(R.id.ScoreTextGameOver)
        val intent = intent
        if (intent.hasExtra("score"))
        {
            score = intent.getIntExtra("score",0)
        }


        val backOKAbout = findViewById<Button>(R.id.ToMenuButton)

        ScoreText.text = getString(R.string.ScoreText) + score.toString()
        backOKAbout.setOnClickListener {

            val intentBack = Intent(this, MainActivity::class.java)
            startActivity(intentBack)
        }
    }
}