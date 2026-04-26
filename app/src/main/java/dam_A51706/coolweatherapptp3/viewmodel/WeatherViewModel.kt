package dam_A51706.coolweatherapptp3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.maps.android.compose.currentCameraPositionState
import dam_A51706.coolweatherapptp3.data.WeatherData
import dam_A51706.coolweatherapptp3.ui.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.InputStreamReader
import java.net.URL

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    private val _weatherUIState= MutableStateFlow(WeatherUIState())
    val weatherUIState: StateFlow<WeatherUIState> = _weatherUIState.asStateFlow()

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

}