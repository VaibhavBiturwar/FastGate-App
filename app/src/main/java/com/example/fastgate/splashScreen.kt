package com.example.fastgate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler


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
        }, 300)


    }
}
