package com.sidharth.mosam.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidharth.mosam.domain.model.WeatherData
import com.sidharth.mosam.domain.usecase.GetWeatherDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherData>()

    val weatherData: LiveData<WeatherData> get() = _weatherData

    fun getWeatherData(
        context: Context,
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            _weatherData.postValue(
                getWeatherDataUseCase.execute(context, latitude, longitude)
            )
        }
    }
}