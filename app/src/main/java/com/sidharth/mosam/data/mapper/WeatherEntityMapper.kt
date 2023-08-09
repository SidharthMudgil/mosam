package com.sidharth.mosam.data.mapper

import androidx.annotation.DrawableRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sidharth.mosam.R
import com.sidharth.mosam.data.local.WeatherEntity
import com.sidharth.mosam.domain.model.DailyForecast
import com.sidharth.mosam.domain.model.Weather
import com.sidharth.mosam.domain.model.WeatherData

object WeatherEntityMapper {

    fun mapWeatherEntityToWeatherData(weatherEntity: WeatherEntity): WeatherData {
        val currentWeather = Weather(
            sunrise = weatherEntity.sunrise,
            sunset = weatherEntity.sunset,
            temperature = weatherEntity.temperature,
            feelsLike = weatherEntity.feelsLike,
            pressure = weatherEntity.pressure,
            humidity = weatherEntity.humidity,
            visibility = weatherEntity.visibility,
            uvi = weatherEntity.uvi,
            windSpeed = weatherEntity.windSpeed,
            windDegree = weatherEntity.windDegree,
            weather = weatherEntity.weather
        )

        return WeatherData(
            background = getBackgroundFromId(weatherEntity.background),
            current = currentWeather,
            forecasts = convertJsonToList(weatherEntity.forecasts)
        )
    }

    fun mapWeatherDataToWeatherEntity(weatherData: WeatherData): WeatherEntity {
        val currentWeather = weatherData.current

        return WeatherEntity(
            background = getIdFromBackground(weatherData.background),
            sunrise = currentWeather.sunrise,
            sunset = currentWeather.sunset,
            temperature = currentWeather.temperature,
            feelsLike = currentWeather.feelsLike,
            pressure = currentWeather.pressure,
            humidity = currentWeather.humidity,
            visibility = currentWeather.visibility,
            uvi = currentWeather.uvi,
            windSpeed = currentWeather.windSpeed,
            windDegree = currentWeather.windDegree,
            weather = currentWeather.weather,
            forecasts = convertListToJson(weatherData.forecasts)
        )
    }

    @DrawableRes
    private fun getBackgroundFromId(id: Int): Int {
        return when (id) {
            0 -> R.drawable.bg_morning
            1 -> R.drawable.bg_day
            2 -> R.drawable.bg_evening
            else -> R.drawable.bg_night
        }
    }

    private fun getIdFromBackground(@DrawableRes id: Int): Int {
        return when (id) {
            R.drawable.bg_morning -> 0
            R.drawable.bg_day -> 1
            R.drawable.bg_evening -> 2
            else -> 3
        }
    }

    private fun convertListToJson(forecasts: List<DailyForecast>): String {
        val jsonList = forecasts.map { forecast ->
            forecast.copy(icon = getIdFromIcon(forecast.icon))
        }
        return Gson().toJson(jsonList)
    }

    private fun convertJsonToList(forecasts: String): List<DailyForecast> {
        val type = object : TypeToken<List<DailyForecast>>() {}.type
        val jsonList: List<DailyForecast> = Gson().fromJson(forecasts, type)
        return jsonList.map { forecast ->
            forecast.copy(icon = getIconFromId(forecast.icon))
        }
    }

    @DrawableRes
    private fun getIconFromId(id: Int): Int {
        return when (id) {
            0 -> R.drawable.ic_clear_day
            1 -> R.drawable.ic_cloudy_day
            2 -> R.drawable.ic_rain
            3 -> R.drawable.ic_thunderstorm
            4 -> R.drawable.ic_snow
            5 -> R.drawable.ic_fog
            else -> R.drawable.ic_tornado
        }
    }

    private fun getIdFromIcon(@DrawableRes id: Int): Int {
        return when (id) {
            R.drawable.ic_clear_day -> 0
            R.drawable.ic_cloudy_day -> 1
            R.drawable.ic_rain -> 2
            R.drawable.ic_thunderstorm -> 3
            R.drawable.ic_snow -> 4
            R.drawable.ic_fog -> 5
            else -> 6
        }
    }
}