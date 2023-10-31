package com.example.bopitmobile

import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.util.Random
import android.widget.TextView

class GameActivity : AppCompatActivity() {

    lateinit var TimerText : TextView
    lateinit var ActionText : TextView
    lateinit var ScoreText : TextView
    var GamePause : Boolean = false
    var countDownTimer : CountDownTimer? = null
    val initialTime = 3000
    var score = 0

    lateinit var themeSoundPlayer : MediaPlayer
    lateinit var victorySoundPlayer : List<MediaPlayer>
    lateinit var defeatSoundPlayer : MediaPlayer

    lateinit var gestureDetector: GestureDetector

    private var randomAction : Int = 0
    private var playbackParams : PlaybackParams? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        themeSoundPlayer = MediaPlayer.create(this, R.raw.theme)
        themeSoundPlayer.isLooping = true
        themeSoundPlayer.start()
        playbackParams = themeSoundPlayer.playbackParams

        victorySoundPlayer = List(5) {MediaPlayer.create(this, R.raw.gain)}
        defeatSoundPlayer = MediaPlayer.create(this, R.raw.error)

        TimerText = findViewById(R.id.TimerText)
        ActionText = findViewById(R.id.ActionText)
        ScoreText = findViewById(R.id.ScoreText)

        gestureDetector = GestureDetector(this, MyGestureListener())

        val handler = Handler()

        handler.postDelayed({
        GameUpdate()
        },500)

    }

    fun GameUpdate(){
        ScoreText.text = getString(R.string.ScoreText) + score.toString()
        randomAction = Random().nextInt(3)

        when (randomAction) {
            0 -> {
                ActionText.setText(R.string.PressText)
            }
            1 -> {
                ActionText.setText(R.string.SwipeText)
            }
            2 -> {
            ActionText.setText(R.string.HoldText)
            }

            3 -> {
                ActionText.setText(R.string.RotateText)
            }


        }
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(initialTime.toLong(),1000) {
            override fun onTick(millisUntilFinished: Long) {

                val secondRemaining = 1 + millisUntilFinished / 1000
                TimerText.text = secondRemaining.toString()

            }

            override fun onFinish() {

                if(GamePause == false) {
                    defeatSoundPlayer.start()
                    GameUpdate()
                }
            }
        }
        countDownTimer?.start()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener()
    {
        override fun onDown(e: MotionEvent): Boolean{
            if(randomAction == 0) {
                score += 100
                val gainsound = victorySoundPlayer.firstOrNull { !it.isPlaying }
                gainsound?.start()
                GameUpdate()
            }
            println("Press")
            return  true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if(randomAction == 1) {
                score += 100
                val gainsound = victorySoundPlayer.firstOrNull { !it.isPlaying }
                gainsound?.start()
                GameUpdate()
            }
            println("Swipe")
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            if(randomAction == 2)
            {
                score += 100
                val gainsound = victorySoundPlayer.firstOrNull { !it.isPlaying }
                gainsound?.start()
                GameUpdate()
            }
            println("Hold")
        }



    }
    override fun onResume() {
        super.onResume()
        GamePause = false;
        themeSoundPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        GamePause = true
        themeSoundPlayer.pause()
    }
}