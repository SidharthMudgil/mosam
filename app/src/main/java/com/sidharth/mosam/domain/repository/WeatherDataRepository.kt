package com.sidharth.mosam.domain.repository

import com.sidharth.mosam.domain.model.WeatherData

interface WeatherDataRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): WeatherData
}