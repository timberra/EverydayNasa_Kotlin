package com.ligagriezne.nasaeveryday.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ligagriezne.nasaeveryday.Error
import com.ligagriezne.nasaeveryday.NasaDataModel
import com.ligagriezne.nasaeveryday.NasaRepository
import com.ligagriezne.nasaeveryday.NasaRepositoryImpl
import com.ligagriezne.nasaeveryday.Success
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class TodayScreenViewModel : ViewModel() {

    private val repo: NasaRepository = NasaRepositoryImpl

    private val _todayPost: MutableLiveData<TodayPostViewModel> = MutableLiveData()
    val todayPost: LiveData<TodayPostViewModel>
        get() = _todayPost

    private val _showToast: MutableLiveData<String> = MutableLiveData()
    val showToast: LiveData<String>
        get() = _showToast

    private val _showLoadError: MutableLiveData<String> = MutableLiveData()
    val showLoadError: LiveData<String>
        get() = _showLoadError


    // Usually should be called on the screen creation or resume from background.
    fun bind() {
        // ViewModel scope will ensure the coroutine call is dropped/cancelled when the ViewModel is destroyed.
        viewModelScope.launch {
            when (val result = repo.getPostByDate(getToday())) {
                is Success -> _todayPost.value = result.data.toViewModel()
                is Error -> _showLoadError.value = "Failed to load today's post"
            }
        }
    }

    fun unbind() {
        // This method is not needed in this case, but it's a good practice to have it.
        // It's called when the screen is destroyed or paused.
        // It's a good place to cancel any ongoing operations.
    }

    private fun getToday(): String = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        .format(Calendar.getInstance().time)

    fun onTitleClick() {
        _showToast.value = "Title was clicked"
    }
}



data class TodayPostViewModel(
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: String,
)

private fun NasaDataModel.toViewModel(): TodayPostViewModel = TodayPostViewModel(
    title = title,
    description = explanation,
    imageUrl = url,
    date = date,
)
