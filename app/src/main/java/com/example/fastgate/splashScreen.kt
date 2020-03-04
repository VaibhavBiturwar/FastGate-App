package com.example.fastgate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.view.animation.AnimationUtils


class splashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        supportActionBar!!.hide()

        Handler().postDelayed(Runnable {
            // This method will be executed once the timer is over
            val i = Intent(this, Login::class.java)
            startActivity(i)
            finish()
        }, 2000)


    }
}
