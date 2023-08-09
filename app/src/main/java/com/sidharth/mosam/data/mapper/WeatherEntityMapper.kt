package com.sidharth.mosam.data.mapper

import com.sidharth.mosam.data.local.DailyForecastEntity
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

        val dailyForecasts = weatherEntity.forecasts.map { forecastEntity ->
            DailyForecast(
                day = forecastEntity.day,
                temp = forecastEntity.temp,
                icon = forecastEntity.icon
            )
        }

        return WeatherData(
            background = weatherEntity.background,
            current = currentWeather,
            forecasts = dailyForecasts
        )
    }

    fun mapWeatherDataToWeatherEntity(weatherData: WeatherData): WeatherEntity {
        val currentWeather = weatherData.current

        val forecastEntities = weatherData.forecasts.map { dailyForecast ->
            DailyForecastEntity(
                day = dailyForecast.day,
                temp = dailyForecast.temp,
                icon = dailyForecast.icon
            )
        }

        return WeatherEntity(
            background = weatherData.background,
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
            forecasts = forecastEntities
        )
    }
}