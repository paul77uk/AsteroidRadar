package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

private val retrofit2 = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
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

    @GET("neo/rest/v1/feed")
    suspend fun getFeed(@Query("api_key") apiKey: String = "G72leUEWda5TavF1K49jqAbwPEoj0oVsp0DxcYSu"): Deferred<NetworkAsteroidContainer>
}

object FeedApi {
    val retrofitService: FeedApiService by lazy {
        retrofit2.create(FeedApiService::class.java)
    }
}