package com.ligagriezne.nasaeveryday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // Delay in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread {
            Thread.sleep(SPLASH_DELAY)
            startActivity(MainActivity.getIntent(this))
            finish()
        }.start()
    }
}

