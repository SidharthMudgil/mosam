package com.sidharth.mosam.data.local

import com.sidharth.mosam.data.mapper.WeatherEntityMapper
import com.sidharth.mosam.domain.model.WeatherData
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao
) {
    suspend fun upsertWeatherData(weatherData: WeatherData) {
        weatherDao.upsertWeatherData(
            WeatherEntityMapper.mapWeatherDataToWeatherEntity(weatherData)
        )
    }

    suspend fun getWeatherData(): WeatherData {
        return WeatherEntityMapper.mapWeatherEntityToWeatherData(
            weatherDao.getWeatherData()
        )
    }
}