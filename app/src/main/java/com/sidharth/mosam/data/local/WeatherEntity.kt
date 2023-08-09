package com.sidharth.mosam.data.local

import androidx.annotation.DrawableRes
import androidx.room.Entity

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @DrawableRes val background: Int,
    val sunrise: String,
    val sunset: String,
    val temperature: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val uvi: Double,
    val windSpeed: Double,
    val windDegree: Int,
    val weather: String,
    val forecasts: List<DailyForecastEntity>
)

@Entity(tableName = "daily_forecast")
data class DailyForecastEntity(
    val day: String,
    val temp: Double,
    @DrawableRes val icon: Int
)