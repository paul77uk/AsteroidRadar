package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.asDatabaseModel
import com.udacity.asteroidradar.network.FeedApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

//@Entity
//data class DatabaseAsteroid constructor(
//    @PrimaryKey
//    val id: Long, val codename: String, val closeApproachDate: String,
//    val absoluteMagnitude: Double, val estimatedDiameter: Double,
//    val relativeVelocity: Double, val distanceFromEarth: Double,
//    val isPotentiallyHazardous: Boolean)
//
//fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
//    return map {
//        Asteroid(
//            id = it.id,
//            codename = it.codename,
//            closeApproachDate = it.closeApproachDate,
//            absoluteMagnitude = it.absoluteMagnitude,
//            estimatedDiameter = it.estimatedDiameter,
//            relativeVelocity = it.relativeVelocity,
//            distanceFromEarth = it.distanceFromEarth,
//            isPotentiallyHazardous = it.isPotentiallyHazardous
//        )
//    }
//}

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val feed = FeedApi.retrofitService.getFeedAsync()
            val parsedFeed = parseAsteroidsJsonResult(JSONObject(feed))
            database.asteroidDao.insertAll(parsedFeed.asDatabaseModel())
        }
    }
}