package dam_A51706.coolweatherapptp3.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam_A51706.coolweatherapptp3.R
import dam_A51706.coolweatherapptp3.ui.theme.WeatherAppTheme

@Composable
fun WeatherCard (
    seaLevelPressure: Float,
    windDirection: Int,
    windSpeed: Float,
    temperature: Float,
    time: String,
    cardmodifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(12.dp),
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = cardmodifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)

            ) {
                Icon(Icons.Default.Cloud, contentDescription = stringResource(R.string.search_location))
                Text(text = stringResource(
                    dam_A51706.coolweatherapptp3.R.string.weather_info),
                    color = colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Icon(Icons.Default.WbSunny, contentDescription = stringResource(R.string.search_location))
            }
            WeatherInfoRow(name = stringResource(R.string.sea_level_pressure), value = "$seaLevelPressure hPa", modifier = Modifier.padding(top = 10.dp))
            WeatherInfoRow(name = stringResource(R.string.wind_direction), value = "$windDirection º")
            WeatherInfoRow(name = stringResource(R.string.wind_speed), value = "$windSpeed km/h")
            WeatherInfoRow(name = stringResource(R.string.temperature), value = "$temperature ºC")
            WeatherInfoRow(name = stringResource(R.string.time), value = time)
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_3")
@Composable
fun WeatherInfoScreenPreview() {
    WeatherAppTheme {
        WeatherCard(0f, 0, 0f, 0f, "00:00")
    }
}