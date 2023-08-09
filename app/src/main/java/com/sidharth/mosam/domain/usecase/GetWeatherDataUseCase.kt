package com.sidharth.mosam.domain.usecase

import com.sidharth.mosam.domain.model.WeatherData

interface GetWeatherDataUseCase {
    suspend fun execute(
        latitude: Double,
        longitude: Double
    ): WeatherData
}