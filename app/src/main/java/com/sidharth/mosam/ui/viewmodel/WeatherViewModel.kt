package com.sidharth.mosam.ui.viewmodel

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

    init {
        viewModelScope.launch {
            _weatherData.postValue(getWeatherDataUseCase.execute())
        }
    }

    suspend fun getWeatherData(): LiveData<WeatherData> {
        _weatherData.postValue(getWeatherDataUseCase.execute())
        return weatherData
    }
}