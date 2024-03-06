package com.ligagriezne.nasaeveryday.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ligagriezne.nasaeveryday.FavoriteItem

class FavoriteFragmentViewModel : ViewModel() {
    private val _favoriteItems: MutableLiveData<List<FavoriteItem>> = MutableLiveData()
    val favoriteItems: LiveData<List<FavoriteItem>>
        get() = _favoriteItems

    // Implement method to fetch data and transform into FavoriteItem objects
    fun fetchData() {
        // Fetch data from API and transform into FavoriteItem objects
        // Update _favoriteItems LiveData with the fetched data
    }
}
