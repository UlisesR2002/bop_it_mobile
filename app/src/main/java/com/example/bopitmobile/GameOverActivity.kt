package com.example.bopitmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.google.android.material.color.utilities.Score

class GameOverActivity : AppCompatActivity() {

    lateinit var ScoreText : TextView
    lateinit var HighScore : TextView
    var score : Int = 0
    var HighScoreValue : String = ""
    var newHighScore : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        ScoreText = findViewById(R.id.ScoreTextGameOver)
        HighScore = findViewById(R.id.HighScoreTextGameOver)

        val intent = intent
        if (intent.hasExtra("score") && intent.hasExtra("newHighScore"))
        {
            score = intent.getIntExtra("score",0)
            newHighScore = intent.getBooleanExtra("newHighScore", false)
        }

        if(newHighScore == true)
        {
            val toast = Toast.makeText(this, R.string.NewHighScore, Toast.LENGTH_SHORT).show()
        }

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        HighScoreValue = preferences.getString("HighScoreSolitary", "0") ?: "0"



        val backOKAbout = findViewById<Button>(R.id.ToMenuButton)

        ScoreText.text = getString(R.string.ScoreText) + score.toString()
        HighScore.text = getString(R.string.HighScoreText) + HighScoreValue

        backOKAbout.setOnClickListener {

            val intentBack = Intent(this, MainActivity::class.java)
            startActivity(intentBack)
        }
    }
}