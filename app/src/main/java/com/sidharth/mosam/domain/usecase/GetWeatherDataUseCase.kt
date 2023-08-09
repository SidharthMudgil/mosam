package com.sidharth.mosam.domain.usecase

import android.content.Context
import com.sidharth.mosam.domain.model.WeatherData

interface GetWeatherDataUseCase {
    suspend fun execute(
        context: Context,
        latitude: Double,
        longitude: Double
    ): WeatherData
}