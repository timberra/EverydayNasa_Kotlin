package com.ligagriezne.nasaeveryday

import android.app.Application
import android.content.Context
import android.util.Log
import coil.ImageLoader
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

class NasaEveryDayApp : Application() {
    val imageLoader: ImageLoader by lazy { createDefaultImageLoader() }
}

private fun Context.createDefaultImageLoader(): ImageLoader = ImageLoader.Builder(this)
    // Build a custom http client that do a image caching.
    .okHttpClient {
        val diskCache = Cache(File(this.cacheDir, "coil"), 1024 * 1024 * 50) // 50MB
        OkHttpClient.Builder()
            .cache(diskCache)
            .build()
    }
    .build()
