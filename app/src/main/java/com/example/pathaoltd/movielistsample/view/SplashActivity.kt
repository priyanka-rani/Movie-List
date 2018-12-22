package com.example.pathaoltd.movielistsample.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.pathaoltd.movielistsample.R
import com.example.pathaoltd.movielistsample.databinding.ActivityMainBinding
import android.content.Intent
import android.preference.PreferenceDataStore


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
