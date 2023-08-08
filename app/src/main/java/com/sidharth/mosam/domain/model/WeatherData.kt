package com.sidharth.mosam.domain.model

import androidx.annotation.DrawableRes

data class WeatherData(
    val today: Weather,
    val daily: List<DailyForecast>
)

data class Weather(
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
    val weather: String
)

data class DailyForecast(
    val day: String,
    val temp: Double,
    @DrawableRes val icon: Int,
)