package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.FeedApi
import com.udacity.asteroidradar.api.PicApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    private val _photo = MutableLiveData<PictureOfDay>()
    val photo: LiveData<PictureOfDay> = _photo

    private val _feed = MutableLiveData<Asteroid>()
    val feed: LiveData<Asteroid> = _feed

    init {
        getPhoto()
    }

    private fun getPhoto() {
        viewModelScope.launch {
            try {
                _photo.value = PicApi.retrofitService.getPhoto()
                _status.value = "   image URL : ${_photo.value!!.url}"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    private fun getNeoFeed() {
        viewModelScope.launch {
            try {
                _feed.value = FeedApi.retrofitService.getFeed()
                _status.value = "   feed URL : ${_feed.value!!.relativeVelocity}"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }


}
