package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid

class DetailViewModelFactory(
    private val asteroidProperty: Asteroid,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(asteroidProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}