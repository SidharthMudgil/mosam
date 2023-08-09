package com.sidharth.mosam.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import com.sidharth.mosam.databinding.ActivityMainBinding
import com.sidharth.mosam.BaseApplication
import com.sidharth.mosam.ui.viewmodel.WeatherViewModel
import com.sidharth.mosam.ui.viewmodel.WeatherViewModelFactory
import com.sidharth.mosam.util.NetworkUtils
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var weatherViewModelFactory: WeatherViewModelFactory

    private val weatherViewModel: WeatherViewModel by viewModels {
        weatherViewModelFactory
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                bindData()
            } else {
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContentView(activityMainBinding.root)

        BaseApplication.instance.appComponent.inject(this)

        if (hasLocationPermission()) {
            bindData()
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        NetworkUtils.startNetworkCallback(this, onConnectionAvailable = {
            weatherViewModel.getWeatherData(0.2, 0.2) // TODO
        }, onConnectionLost = {})
        setupTransitionGenerator()
        bindData()
    }

    override fun onPause() {
        super.onPause()
        NetworkUtils.stopNetworkCallback(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkUtils.stopNetworkCallback(this)
    }

    @SuppressLint("SetTextI18n")
    private fun bindData() {
        weatherViewModel.getWeatherData(34.4, 23.3).observe(this) {// TODO
            activityMainBinding.bgImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    it.background
                )
            )
            activityMainBinding.currTemperature.text = it.current.temperature.toString()
            activityMainBinding.weather.text = it.current.weather
            activityMainBinding.dataSunrise.text = it.current.sunrise
            activityMainBinding.dataSunset.text = it.current.sunset
            activityMainBinding.dataWindSpeed.text = "${it.current.windSpeed}m/s"
            activityMainBinding.dataWindDirection.text = "${it.current.windDegree}°"
            activityMainBinding.dataFeel.text = "${it.current.feelsLike}°C"
            activityMainBinding.dataPressure.text = "${it.current.pressure}hPa"
            activityMainBinding.dataHumidity.text = "${it.current.humidity}%"
            activityMainBinding.dataVisibility.text = "${it.current.visibility}m"
            activityMainBinding.dataUvi.text = "${it.current.uvi}"

            activityMainBinding.labelDay1.text = it.forecasts[0].day
            activityMainBinding.labelDay2.text = it.forecasts[1].day
            activityMainBinding.labelDay3.text = it.forecasts[2].day
            activityMainBinding.labelDay4.text = it.forecasts[3].day
            activityMainBinding.labelDay5.text = it.forecasts[4].day
            activityMainBinding.labelDay6.text = it.forecasts[5].day
            activityMainBinding.labelDay7.text = it.forecasts[6].day
            activityMainBinding.labelDay8.text = it.forecasts[7].day

            activityMainBinding.dataDay1.text = "${it.forecasts[0].temp}°C"
            activityMainBinding.dataDay2.text = "${it.forecasts[1].temp}°C"
            activityMainBinding.dataDay3.text = "${it.forecasts[2].temp}°C"
            activityMainBinding.dataDay4.text = "${it.forecasts[3].temp}°C"
            activityMainBinding.dataDay5.text = "${it.forecasts[4].temp}°C"
            activityMainBinding.dataDay6.text = "${it.forecasts[5].temp}°C"
            activityMainBinding.dataDay7.text = "${it.forecasts[6].temp}°C"
            activityMainBinding.dataDay8.text = "${it.forecasts[7].temp}°C"

            activityMainBinding.icDay1.setImageDrawable(
                AppCompatResources.getDrawable(this, it.forecasts[0].icon)
            )
            activityMainBinding.icDay2.setImageDrawable(
                AppCompatResources.getDrawable(this, it.forecasts[1].icon)
            )
            activityMainBinding.icDay3.setImageDrawable(
                AppCompatResources.getDrawable(this, it.forecasts[2].icon)
            )
            activityMainBinding.icDay4.setImageDrawable(
                AppCompatResources.getDrawable(this, it.forecasts[3].icon)
            )
            activityMainBinding.icDay5.setImageDrawable(
                AppCompatResources.getDrawable(this, it.forecasts[4].icon)
            )
            activityMainBinding.icDay6.setImageDrawable(
                AppCompatResources.getDrawable(this, it.forecasts[5].icon)
            )
            activityMainBinding.icDay7.setImageDrawable(
                AppCompatResources.getDrawable(this, it.forecasts[6].icon)
            )
            activityMainBinding.icDay8.setImageDrawable(
                AppCompatResources.getDrawable(this, it.forecasts[7].icon)
            )
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun setupTransitionGenerator() {
        activityMainBinding.bgImage.setTransitionGenerator(
            RandomTransitionGenerator(
                TRANSITION_DURATION,
                AccelerateDecelerateInterpolator()
            )
        )
    }

    companion object {
        private const val TRANSITION_DURATION = 30000L
    }
}