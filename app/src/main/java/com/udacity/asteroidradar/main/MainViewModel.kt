package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.FeedApi
import com.udacity.asteroidradar.network.PicApi
import com.udacity.asteroidradar.network.asDomainModel
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    private val _photo = MutableLiveData<PictureOfDay>()
    val photo: LiveData<PictureOfDay> = _photo

//    private val _feed = MutableLiveData<List<Asteroid>>()
//    val feed: LiveData<List<Asteroid>> = _feed

    private val database = getDatabase(application)

    private val asteroidsRepository = AsteroidsRepository(database)

    private val _navigateToSelectedAsteroid = MutableLiveData<DatabaseAsteroid>()
    val navigateToSelectedAsteroid: LiveData<DatabaseAsteroid>
        get() = _navigateToSelectedAsteroid

    init {
        getPhoto()
//        getNeoFeed()
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

    val feed = asteroidsRepository.asteroids

    private fun getPhoto() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    PicApi.retrofitService.getPhoto()
                }
                _photo.value = result
                _status.value = "   image URL : ${_photo.value!!.url}"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

//    private fun getNeoFeed() {
//        viewModelScope.launch {
//            try {
//                val result = withContext(Dispatchers.IO) {
//                    FeedApi.retrofitService.getFeed().await()
//                }
////                val asteroids = parseAsteroidsJsonResult(JSONObject(result))
//                _feed.postValue(result.asDomainModel())
//                _status.value = "Success"
//            } catch (e: Exception) {
//                _status.value = "Failure: ${e.message}"
//            }
//        }
//    }

    fun displayPropertyDetails(asteroid: DatabaseAsteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

}
