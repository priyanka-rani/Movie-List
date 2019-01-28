package com.pri.movielistsample.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pri.movielistsample.R
import android.content.Intent


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                this@SplashActivity.finish()
        }, 2*1000)//splash for 2 seconds
    }
}
