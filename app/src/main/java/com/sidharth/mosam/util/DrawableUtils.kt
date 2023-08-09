package com.sidharth.mosam.util

import androidx.annotation.DrawableRes
import com.sidharth.mosam.R
import java.util.Calendar
import java.util.Locale

object DrawableUtils {
    @DrawableRes
    fun getBackgroundBasedOnTime(timestamp: Long): Int {
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
    fun getIconForWeather(weatherMain: String): Int {
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