package com.sidharth.mosam.data.mapper

import androidx.annotation.DrawableRes
import com.sidharth.mosam.R
import com.sidharth.mosam.data.remote.CurrentWeather
import com.sidharth.mosam.data.remote.DailyWeather
import com.sidharth.mosam.data.remote.WeatherResponse
import com.sidharth.mosam.domain.model.DailyForecast
import com.sidharth.mosam.domain.model.Weather
import com.sidharth.mosam.domain.model.WeatherData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeatherMapper {

    fun mapWeatherResponseToWeatherData(response: WeatherResponse): WeatherData {
        val currentWeather = mapCurrentWeather(response.current)
        val dailyWeatherList = mapDailyWeather(response.daily)
        return WeatherData(currentWeather, dailyWeatherList)
    }

    private fun mapCurrentWeather(current: CurrentWeather): Weather {
        val weatherDescription = current.weather.firstOrNull()?.description.orEmpty()
        val background = getBackgroundBasedOnTime(current.dt)
        return Weather(
            background = background,
            sunrise = current.sunrise.toHMM(),
            sunset = current.sunset.toHMM(),
            temperature = current.temp,
            feelsLike = current.feelsLike,
            pressure = current.pressure,
            humidity = current.humidity,
            visibility = current.visibility,
            uvi = current.uvi,
            windSpeed = current.windSpeed,
            windDegree = current.windDeg,
            weather = weatherDescription
        )
    }

    private fun Long.toHMM(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date(this * 1000))
    }

    private fun Long.toEEE(): String {
        val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        return dateFormat.format(Date(this * 1000))
    }

    private fun mapDailyWeather(daily: List<DailyWeather>): List<DailyForecast> {
        return daily.map {
            DailyForecast(
                day = it.dt.toEEE(),
                temp = it.temp.day,
                icon = getIconForWeather(it.weather.firstOrNull()?.main.orEmpty())
            )
        }
    }

    @DrawableRes
    private fun getBackgroundBasedOnTime(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 5..10 -> R.drawable.bg_morning
            in 11..17 -> R.drawable.bg_day
            in 18..19 -> R.drawable.bg_evening
            else -> R.drawable.bg_night
        }
    }

    @DrawableRes
    private fun getIconForWeather(weatherMain: String): Int {
        return when (weatherMain.lowercase(Locale.ROOT)) {
            "clouds" -> R.drawable.ic_cloudy_day
            "rain" -> R.drawable.ic_rain
            "snow" -> R.drawable.ic_snow
            "thunderstorm" -> R.drawable.ic_thunderstorm
            "fog" -> R.drawable.ic_fog
            else -> R.drawable.ic_clear_day
        }
    }
}
