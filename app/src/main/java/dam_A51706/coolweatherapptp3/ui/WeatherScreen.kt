package dam_A51706.coolweatherapptp3.ui

import dam_A51706.coolweatherapptp3.viewmodel.WeatherViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dam_A51706.coolweatherapptp3.data.WMO_WeatherCode
import dam_A51706.coolweatherapptp3.data.getWeatherCodeMap
import dam_A51706.coolweatherapptp3.ui.theme.WeatherAppTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam_A51706.coolweatherapptp3.R

@Composable
fun WeatherUI (weatherViewModel: WeatherViewModel = viewModel() ) {
    val weatherUIState by weatherViewModel.weatherUIState.collectAsState()

    val latitude = weatherUIState.latitude
    val longitude = weatherUIState.longitude
    val temperature = weatherUIState.temperature
    val windSpeed = weatherUIState.windSpeed
    val windDirection = weatherUIState.windDirection
    val weatherCode = weatherUIState.weatherCode
    val seaLevelPressure = weatherUIState.seaLevelPressure
    val time = weatherUIState.time

    val configuration = LocalConfiguration.current
    val hour = time.substringAfter("T").substringBefore(":").toIntOrNull() ?: 12
    val day = hour in 6..19
    val mapt = getWeatherCodeMap()
    val wCode = mapt.get(weatherCode)
    val wImage = when(wCode) {
        WMO_WeatherCode.CLEAR_SKY ,
        WMO_WeatherCode.MAINLY_CLEAR ,
        WMO_WeatherCode.PARTLY_CLOUDY -> if (day) wCode?.image + "day" else wCode?.image + "night"
        else -> wCode?.image
    }

    val context = LocalContext.current
    val wIcon = context.resources.getIdentifier(wImage, "drawable", context.packageName )
    if ( configuration.orientation == Configuration.ORIENTATION_LANDSCAPE ) {
        LandscapeWeatherUI (
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weatherCode,
            seaLevelPressure,
            time,
            onLatitudeChange = {
                    newValue -> newValue.toFloatOrNull()?.let {
                weatherViewModel.updateLatitude(it) }
            } ,
            onLongitudeChange = {
                    newValue -> newValue.toFloatOrNull()?.let {
                weatherViewModel.updateLongitude(it) }
            } ,
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            },
        )
    } else {
        PortraitWeatherUI (
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weatherCode,
            seaLevelPressure,
            time,
            onLatitudeChange = {
                newValue ->
                newValue.toFloatOrNull()?.let {
                    weatherViewModel.updateLatitude(it) }
            },
            onLongitudeChange = {
                newValue ->
                newValue.toFloatOrNull()?.let {
                    weatherViewModel.updateLongitude(it) }
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            },
        )
    }
}


@Composable
fun WeatherUIPreview () {

    val latitude = 20f
    val longitude = 113f
    val temperature = 20f
    val windSpeed = 13f
    val windDirection = 250
    val weatherCode = 0
    val seaLevelPressure = 0f
    val time = stringResource(R.string.time)

    val configuration = LocalConfiguration.current
    val day = false // Must change this in the future
    val mapt = getWeatherCodeMap()
    val wCode = mapt.get(weatherCode)
    val wImage = when(wCode) {
        WMO_WeatherCode.CLEAR_SKY ,
        WMO_WeatherCode.MAINLY_CLEAR ,
        WMO_WeatherCode.PARTLY_CLOUDY -> if (day) wCode?.image + "day" else wCode?.image + "night"
        else -> wCode?.image
    }

    val context = LocalContext.current
    val wIcon = context.resources.getIdentifier(wImage, "drawable", context.packageName )
    if ( configuration.orientation == Configuration.ORIENTATION_LANDSCAPE ) {
        LandscapeWeatherUI (
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weatherCode,
            seaLevelPressure,
            time,
            onLatitudeChange = {
            } ,
            onLongitudeChange = {
            } ,
            onUpdateButtonClick = {
            }
        )
    } else {
        PortraitWeatherUI (
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weatherCode,
            seaLevelPressure,
            time,
            onLatitudeChange = {
            },
            onLongitudeChange = {
            },
            onUpdateButtonClick = {
            },

        )
    }
}


@Composable
fun PortraitWeatherUI (
    wIcon : Int,
    latitude : Float,
    longitude : Float,
    temperature : Float,
    windSpeed : Float,
    windDirection : Int,
    weatherCode : Int,
    seaLevelPressure : Float,
    time : String,
    onLatitudeChange : (String) -> Unit,
    onLongitudeChange : (String) -> Unit,
    onUpdateButtonClick : () -> Unit,
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Image(
                painter = painterResource(id = wIcon), contentDescription = stringResource(R.string.weather_image),
                modifier = Modifier.size(125.dp)
            )
            CoordinatesCard(latitude, longitude, onLatitudeChange, onLongitudeChange)
            WeatherCard(seaLevelPressure, windDirection, windSpeed, temperature, time)
            Button(
                onClick = onUpdateButtonClick,
                modifier = Modifier.width(300.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.update),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun LandscapeWeatherUI (
    wIcon : Int,
    latitude : Float,
    longitude : Float,
    temperature : Float,
    windSpeed : Float,
    windDirection : Int,
    weatherCode : Int,
    seaLevelPressure : Float,
    time : String,
    onLatitudeChange : (String) -> Unit,
    onLongitudeChange : (String) -> Unit,
    onUpdateButtonClick : () -> Unit,
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp, vertical = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.5F)
                ) {
                    Image(
                        painter = painterResource(id = wIcon), contentDescription = stringResource(R.string.weather_image),
                        modifier = Modifier.size(125.dp)
                    )
                    Button(
                        onClick = onUpdateButtonClick,
                        modifier = Modifier.width(200.dp).height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.update),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
                CoordinatesCard(latitude, longitude, onLatitudeChange, onLongitudeChange, Modifier.weight(0.6F).wrapContentHeight().padding(12.dp))
                WeatherCard(seaLevelPressure, windDirection, windSpeed, temperature, time, Modifier.weight(1F).wrapContentHeight().padding(12.dp))
            }
        }
    }
}


@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,orientation=portrait")
@Composable
fun AppScreenPreview() {
    WeatherAppTheme {
        WeatherUIPreview()
    }
}

//Landscape

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,orientation=landscape", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AppScreenPreviewLandscape() {
    WeatherAppTheme {
        WeatherUIPreview()
    }
}
