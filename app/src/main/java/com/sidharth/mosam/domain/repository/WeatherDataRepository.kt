package com.sidharth.mosam.domain.repository

import android.content.Context
import com.sidharth.mosam.domain.model.WeatherData

interface WeatherDataRepository {
    suspend fun getWeatherData(
        context: Context,
        latitude: Double,
        longitude: Double
    ): WeatherData
}