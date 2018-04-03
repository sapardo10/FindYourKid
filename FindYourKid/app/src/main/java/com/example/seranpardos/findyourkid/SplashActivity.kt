package com.example.seranpardos.findyourkid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Activity that models the intro screen of the app
 * First activity of the App
 */
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //The animation to make the transition into the MainActivity of the App
        var transition: Animation = AnimationUtils.loadAnimation(this, R.anim.maintransition)
        //The animation is started
        textWelcome.startAnimation(transition)
        imageLogo.startAnimation(transition)
        //An intent is create to go to MainActivity after the animation is over
        val intent = Intent(this, MainActivity::class.java)

        /**
         * Thread object to delay the start the MainActivity (5 seconds)
         * code based from: https://gist.github.com/dharmakshetri/2471b522b929721363c81f9da386bacd
         */
        val timer = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 5 seconds
                    Thread.sleep((5 * 1000).toLong())

                    // After 5 seconds redirect to another intent
                    startActivity(intent)
                    //Remove activity
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        //The timer starts
        timer.start()
    }
}
