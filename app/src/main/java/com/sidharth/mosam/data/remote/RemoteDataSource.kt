package com.sidharth.mosam.data.remote

import com.sidharth.mosam.data.mapper.WeatherResponseMapper
import com.sidharth.mosam.domain.model.EmptyWeatherData
import com.sidharth.mosam.domain.model.WeatherData
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val weatherService: WeatherService
) {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): WeatherData {
        val response = weatherService.getWeatherData(latitude, longitude)
        if (response.isSuccessful) {
            response.body()?.let {
                return WeatherResponseMapper.mapWeatherResponseToWeatherData(it)
            }
        }
        return EmptyWeatherData.instance
    }
}