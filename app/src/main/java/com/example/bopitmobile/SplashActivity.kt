package com.example.bopitmobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    val tagLog = "SplashActivity"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)



        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = sharedPreferences.getString("splash_time", "1000")

        Log.i(tagLog,"Value of splash_time:" + value)

        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)

        val scaleUp = AnimationUtils.loadAnimation(applicationContext, R.anim.scaleup)

        val animationSet = AnimationSet(true)
        animationSet.addAnimation(fadeIn)
        animationSet.addAnimation(scaleUp)

        val imageView = findViewById<ImageView>(R.id.imageView)

        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Animation started
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Animation ended; proceed to MainActivity
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional: Close the splash screen activity
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Animation repeated
            }
        })

        imageView.startAnimation(fadeIn)
        val seconds = value?.toLong()
        val delayMillis = seconds!!.toLong() // 2 seconds (adjust as needed)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the splash screen activity
        }, delayMillis)

    }
}