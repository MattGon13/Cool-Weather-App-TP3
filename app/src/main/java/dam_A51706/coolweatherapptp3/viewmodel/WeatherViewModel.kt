package dam_A51706.coolweatherapptp3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dam_A51706.coolweatherapptp3.data.Favourite
import dam_A51706.coolweatherapptp3.data.WeatherApiClient
import dam_A51706.coolweatherapptp3.ui.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.Float

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    private val _weatherUIState= MutableStateFlow(WeatherUIState())
    val weatherUIState: StateFlow<WeatherUIState> = _weatherUIState.asStateFlow()

    private val _favourites = mutableListOf<Favourite>()
    val favourites: List<Favourite> = _favourites

    fun updateLatitude ( newLatitude: Float ){
        if( newLatitude in -90f..90f ){
            _weatherUIState.update { currentState ->
                currentState.copy(latitude = newLatitude)
            }
        }
    }

    fun updateLongitude( newLongitude: Float ){
        if( newLongitude in -180f..180f ){
            _weatherUIState.update { currentState ->
                currentState.copy(longitude = newLongitude)
            }
        }
    }

    fun fetchWeather() {
        viewModelScope.launch {
            val weatherData = WeatherApiClient.getWeather(_weatherUIState.value.latitude, _weatherUIState.value.longitude)

            val current = weatherData?.current_weather
            val hourly = weatherData?.hourly

            if(weatherData != null && current != null && hourly != null){
                _weatherUIState.update { currentState ->
                    currentState.copy(
                        temperature = current.temperature,
                        windSpeed = current.windspeed,
                        windDirection = current.winddirection,
                        weatherCode = current.weathercode,
                        seaLevelPressure = hourly.pressure_msl[12].toFloat(),
                        time = current.time
                    )
                }
            }
        }
    }

    fun addFavourite(favourite: Favourite){
        _favourites.add(favourite)
    }

    fun selectFavourite(favourite: Favourite){
        updateLatitude(favourite.latitude)
        updateLongitude(favourite.longitude)
        fetchWeather()
    }
}