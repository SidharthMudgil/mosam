package com.sidharth.mosam.domain.model

import androidx.annotation.DrawableRes
import com.sidharth.mosam.R

data class WeatherData(
    @DrawableRes val background: Int,
    val current: Weather,
    val forecasts: List<DailyForecast>
)

data class Weather(
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

object EmptyWeatherData {
    val instance: WeatherData = WeatherData(
        background = R.drawable.bg_day,
        current = Weather(
            sunrise = "00:00",
            sunset = "00:00",
            temperature = 0.0,
            feelsLike = 0.0,
            pressure = 0,
            humidity = 0,
            visibility = 0,
            uvi = 0.0,
            windSpeed = 0.0,
            windDegree = 0,
            weather = "No Data"
        ),
        forecasts = listOf(
            DailyForecast("Nil", 0.0, R.drawable.ic_clear_day),
            DailyForecast("Nil", 0.0, R.drawable.ic_clear_day),
            DailyForecast("Nil", 0.0, R.drawable.ic_clear_day),
            DailyForecast("Nil", 0.0, R.drawable.ic_clear_day),
            DailyForecast("Nil", 0.0, R.drawable.ic_clear_day),
            DailyForecast("Nil", 0.0, R.drawable.ic_clear_day),
            DailyForecast("Nil", 0.0, R.drawable.ic_clear_day),
            DailyForecast("Nil", 0.0, R.drawable.ic_clear_day)
        )
    )
}