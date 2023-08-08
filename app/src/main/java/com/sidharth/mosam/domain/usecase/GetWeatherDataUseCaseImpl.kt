package com.sidharth.mosam.domain.usecase

import com.sidharth.mosam.domain.model.WeatherData
import com.sidharth.mosam.domain.repository.WeatherDataRepository
import javax.inject.Inject

class GetWeatherDataUseCaseImpl @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
): GetWeatherDataUseCase {
    override suspend fun execute(): WeatherData {
        return weatherDataRepository.getWeatherData()
    }
}