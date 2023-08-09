package com.sidharth.mosam.util

import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import android.Manifest

object LocationUtils {

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getCurrentLocation(context: Context): Location? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }

        return null
    }
}
