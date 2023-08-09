package com.sidharth.mosam.data.repository

import com.sidharth.mosam.data.local.LocalDataSource
import com.sidharth.mosam.data.remote.RemoteDataSource
import com.sidharth.mosam.domain.model.WeatherData
import com.sidharth.mosam.domain.repository.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : WeatherDataRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): WeatherData {
        val hasInternet = true
        return if (hasInternet) {
            val weatherData = remoteDataSource.getWeatherData(latitude, longitude)
            localDataSource.upsertWeatherData(weatherData)
            weatherData
        } else {
            localDataSource.getWeatherData()
        }
    }
}