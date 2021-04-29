package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.main.MainViewModel
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class DetailViewModel(asteroid: Asteroid, app: Application) : AndroidViewModel(app)  {

    private val database = getDatabase(app)

    private val asteroidsRepository = AsteroidsRepository(database)

    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid

    init {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
        _selectedAsteroid.value = asteroid

    }

    val feed = asteroidsRepository.asteroids

}