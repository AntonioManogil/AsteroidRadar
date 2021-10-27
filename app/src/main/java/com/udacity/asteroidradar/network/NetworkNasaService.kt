package com.udacity.asteroidradar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.nasa.gov/planetary/apod?api_key=24fZsZnet2eyRqqkHg21MbTo4gzUGDbMXIWUavR5
private const val BASE_URL = "https://api.nasa.gov"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofitMoshi = Retrofit.Builder().baseUrl(BASE_URL)
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .addCallAdapterFactory(CoroutineCallAdapterFactory())
  .build()

private val retrofitScalar = Retrofit.Builder().baseUrl(BASE_URL)
  .addConverterFactory(ScalarsConverterFactory.create())
  .addCallAdapterFactory(CoroutineCallAdapterFactory())
  .build()

interface NetworkImageOfDayService {
  @GET("planetary/apod")
  fun getNetworkImageOfDayAsync(@Query("api_key") api_key: String): Deferred<NetworkImageOfDay>
}
interface NetworkAsteroidsService {
  @GET("neo/rest/v1/feed")
  fun getAsteroidsAsync(@Query("start_date") start_date: String,
                   @Query("end_date") end_date: String,
                   @Query("api_key") api_key: String): Deferred<String>
}

object Network{
  val imageOfDayService: NetworkImageOfDayService by lazy{
    retrofitMoshi.create(NetworkImageOfDayService::class.java)
  }
  val asteroidsService: NetworkAsteroidsService by lazy{
    retrofitScalar.create(NetworkAsteroidsService::class.java)
  }
}