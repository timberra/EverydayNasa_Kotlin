package com.ligagriezne.nasaeveryday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // Delay in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            startActivity(MainActivity.getIntent(this@SplashActivity))
            finish()
        }
    }
}

