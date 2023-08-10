package com.sidharth.mosam.util

import androidx.annotation.DrawableRes
import com.sidharth.mosam.R
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object DrawableUtils {
    @DrawableRes
    fun getBackgroundBasedOnTime(timestamp: Long, timezone: String): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000
        calendar.timeZone = TimeZone.getTimeZone(timezone)
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 5..9 -> R.drawable.bg_morning
            in 10..17 -> R.drawable.bg_day
            in 18..19 -> R.drawable.bg_evening
            else -> R.drawable.bg_night
        }
    }

    @DrawableRes
    fun getIconForWeather(weatherMain: String): Int {
        return when (weatherMain.lowercase(Locale.ROOT)) {
            "clouds", "drizzle" -> R.drawable.ic_cloudy_day
            "rain" -> R.drawable.ic_rain
            "snow" -> R.drawable.ic_snow
            "thunderstorm" -> R.drawable.ic_thunderstorm
            "fog", "mist", "smoke", "haze", "dust", "sand", "ash", "squall" -> R.drawable.ic_fog
            "tornado" -> R.drawable.ic_tornado
            else -> R.drawable.ic_clear_day
        }
    }
}