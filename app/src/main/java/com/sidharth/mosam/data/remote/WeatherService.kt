package com.sidharth.mosam.data.remote

import com.sidharth.mosam.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "minutely,hourly",
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
    ): Response<WeatherResponse>
}
