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
import com.sidharth.mosam.BaseApplication
import com.sidharth.mosam.databinding.ActivityMainBinding
import com.sidharth.mosam.ui.viewmodel.WeatherViewModel
import com.sidharth.mosam.ui.viewmodel.WeatherViewModelFactory
import com.sidharth.mosam.util.LocationUtils
import com.sidharth.mosam.util.NetworkUtils
import java.math.RoundingMode
import java.text.DecimalFormat
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
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineLocationGranted || coarseLocationGranted) {
                getWeatherData()
            } else {
                finish()
            }
        }

    override fun onPause() {
        super.onPause()
        NetworkUtils.stopNetworkCallback(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkUtils.stopNetworkCallback(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContentView(activityMainBinding.root)
        initDependencyInjection()
        setupNetworkCallback()
        setupTransitionGenerator()
        observeBindWeatherData()
        getWeatherData()
    }

    private fun initDependencyInjection() {
        BaseApplication.instance.appComponent.inject(this)
    }

    private fun getWeatherData() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationUtils.getCurrentLocation(applicationContext)?.let {
                weatherViewModel.getWeatherData(applicationContext, it.latitude, it.longitude)
            } ?: weatherViewModel.getWeatherData(applicationContext, 0.0, 0.0)
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun setupNetworkCallback() {
        NetworkUtils.startNetworkCallback(
            context = applicationContext,
            onConnectionAvailable = { getWeatherData() },
            onConnectionLost = {}
        )
    }

    @SuppressLint("SetTextI18n")
    private fun observeBindWeatherData() {
        weatherViewModel.weatherData.observe(this) {
            activityMainBinding.bgImage.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    it.background
                )
            )
            activityMainBinding.currTemperature.text = it.current.temperature.toInt().toString()
            activityMainBinding.weather.text = it.current.weather
            activityMainBinding.dataSunrise.text = it.current.sunrise
            activityMainBinding.dataSunset.text = it.current.sunset
            activityMainBinding.dataWindSpeed.text = "${it.current.windSpeed} m/s"
            activityMainBinding.dataWindDirection.text = "${it.current.windDegree}°"
            activityMainBinding.dataFeel.text = "${it.current.feelsLike}°C"
            activityMainBinding.dataPressure.text = "${it.current.pressure} hPa"
            activityMainBinding.dataHumidity.text = "${it.current.humidity}%"
            activityMainBinding.dataVisibility.text =
                "${roundOffDecimal(it.current.visibility / 1000.0)} km"
            activityMainBinding.dataUvi.text = "${it.current.uvi}"

            activityMainBinding.labelDay1.text = it.forecasts[0].day
            activityMainBinding.labelDay2.text = it.forecasts[1].day
            activityMainBinding.labelDay3.text = it.forecasts[2].day
            activityMainBinding.labelDay4.text = it.forecasts[3].day
            activityMainBinding.labelDay5.text = it.forecasts[4].day
            activityMainBinding.labelDay6.text = it.forecasts[5].day
            activityMainBinding.labelDay7.text = it.forecasts[6].day
            activityMainBinding.labelDay8.text = it.forecasts[7].day

            activityMainBinding.dataDay1.text = "${it.forecasts[0].temp.toInt()}°"
            activityMainBinding.dataDay2.text = "${it.forecasts[1].temp.toInt()}°"
            activityMainBinding.dataDay3.text = "${it.forecasts[2].temp.toInt()}°"
            activityMainBinding.dataDay4.text = "${it.forecasts[3].temp.toInt()}°"
            activityMainBinding.dataDay5.text = "${it.forecasts[4].temp.toInt()}°"
            activityMainBinding.dataDay6.text = "${it.forecasts[5].temp.toInt()}°"
            activityMainBinding.dataDay7.text = "${it.forecasts[6].temp.toInt()}°"
            activityMainBinding.dataDay8.text = "${it.forecasts[7].temp.toInt()}°"

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

    private fun setupTransitionGenerator() {
        activityMainBinding.bgImage.setTransitionGenerator(
            RandomTransitionGenerator(
                TRANSITION_DURATION,
                AccelerateDecelerateInterpolator()
            )
        )
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

    companion object {
        private const val TRANSITION_DURATION = 30000L
    }
}