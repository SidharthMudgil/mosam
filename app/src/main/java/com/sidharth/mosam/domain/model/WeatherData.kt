package com.sidharth.mosam.domain.model

import androidx.annotation.DrawableRes

data class WeatherData(
    val current: CurrentWeather,
    val daily: List<DailyWeather>
)

data class CurrentWeather(
    @DrawableRes val background: Int,
    val sunrise: Long,
    val sunset: Long,
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

data class DailyWeather(
    val day: Long,
    val temp: Double,
    @DrawableRes val icon: Int,
)