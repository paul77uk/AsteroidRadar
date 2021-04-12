package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface PicApiService {

    @GET("planetary/apod")
    suspend fun getPhoto(@Query("api_key") apiKey: String = "G72leUEWda5TavF1K49jqAbwPEoj0oVsp0DxcYSu"): PictureOfDay
}

object PicApi {
    val retrofitService: PicApiService by lazy {
        retrofit.create(PicApiService::class.java)
    }
    
}

interface FeedApiService {
    @GET("neo/rest/feed?start_date=2015-09-07&end_date=2015-09-08")
    suspend fun getFeed(@Query("api_key") apiKey: String = "G72leUEWda5TavF1K49jqAbwPEoj0oVsp0DxcYSu"): Asteroid
}

object FeedApi {
    val retrofitService: FeedApiService by lazy {
        retrofit.create(FeedApiService::class.java)
    }

}
