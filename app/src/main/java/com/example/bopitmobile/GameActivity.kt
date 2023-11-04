package com.example.bopitmobile

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import java.util.Random
import kotlin.math.abs


class GameActivity : AppCompatActivity(), SensorEventListener {

    lateinit var TimerText : TextView
    lateinit var ActionText : TextView
    lateinit var ScoreText : TextView
    lateinit var HighScore : TextView

    var GamePause : Boolean = false
    var countDownTimer : CountDownTimer? = null
    val initialTime = 3000

    var score = 0
    var highscore : String = ""


    lateinit var themeSoundPlayer : MediaPlayer
    lateinit var victorySoundPlayer : List<MediaPlayer>
    lateinit var defeatSoundPlayer : MediaPlayer

    lateinit var gestureDetector: GestureDetector

    private lateinit var sensorManager: SensorManager
    private var accelerometer : Sensor? = null
    private var lastAcceleration : Float = 0f
    private var shakeThreshold : Float = 0.01f
    private var lastUpdate: Long = 0

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

        TimerText = findViewById(R.id.ScoreTextGameOver)
        ActionText = findViewById(R.id.GameOverText)
        ScoreText = findViewById(R.id.ScoreText)
        HighScore = findViewById(R.id.HighScoreText)


        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        //highscore = sharedPreferences.getString("HighScore", R.string.HighScore)!!

        gestureDetector = GestureDetector(this, MyGestureListener())

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val handler = Handler()

        handler.postDelayed({
        GameUpdate()
        },500)
    }

    fun GameOver(){
        defeatSoundPlayer.start()
        val intentGameOver = Intent(this, GameOverActivity::class.java)
        intentGameOver.putExtra("score", score)
        startActivity(intentGameOver)
    }
    fun GameUpdate(){
        ScoreText.text = getString(R.string.ScoreText) + score.toString()
        //HighScore.text = getString(R.string.HighScoreText) + highscore
        randomAction = Random().nextInt(4)

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
                ActionText.setText(R.string.ShakeText)
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
                    //GameUpdate()
                    GameOver()
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
        override fun onSingleTapUp(e: MotionEvent): Boolean{
            if(randomAction == 0) {
                score += 100
                val gainsound = victorySoundPlayer.firstOrNull { !it.isPlaying }
                gainsound?.start()
                GameUpdate()
            }else{
                GameOver()
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
            }else{
                GameOver()
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
            }else{
                GameOver()
            }
            println("Hold")
        }



    }
    override fun onResume() {
        super.onResume()
        GamePause = false;
        themeSoundPlayer.start()

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        GamePause = true
        themeSoundPlayer.pause()

        sensorManager.unregisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        themeSoundPlayer.release()
        defeatSoundPlayer.release()

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            if(currentTime - lastUpdate > 100) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val acceleration = abs(x + y + z - lastAcceleration) / (currentTime - lastUpdate)

                if (acceleration > shakeThreshold) {
                    if (randomAction == 3) {
                        score += 100
                        val gainsound = victorySoundPlayer.firstOrNull { !it.isPlaying }
                        gainsound?.start()
                        GameUpdate()
                    } else {
                        GameOver()
                    }
                }
                lastUpdate = currentTime
                lastAcceleration = x + y + z
            }
        }
    }
}