package com.ligagriezne.nasaeveryday.Fragments

import android.widget.ImageView
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.ligagriezne.nasaeveryday.NasaEveryDayApp
import com.ligagriezne.nasaeveryday.R

fun ImageView.loadImage(url: String) {
    val imageLoader = (context.applicationContext as NasaEveryDayApp).imageLoader
    val request = ImageRequest.Builder(context)
        .data(url)
        .target(this)
        .placeholder(R.drawable.image_not_found)
        .error(R.drawable.image_not_found)
        .memoryCacheKey(MemoryCache.Key(url))
        .crossfade(true)
        .build()
    imageLoader.enqueue(request)
}




