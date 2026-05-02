package dam_A51706.coolweatherapptp3.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dam_A51706.coolweatherapptp3.ui.theme.WeatherAppTheme
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.height
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import dam_A51706.coolweatherapptp3.LocationPickerActivity
import dam_A51706.coolweatherapptp3.viewmodel.WeatherViewModel

@Composable
fun CoordinatesCard (
    lat: Float,
    long: Float,
    onLatChange: (String) -> Unit,
    onLogChange: (String) -> Unit,
    cardModifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    viewModel: WeatherViewModel,
) {
    var latInput by remember{ mutableStateOf(lat.toString()) }
    var longInput by remember{ mutableStateOf(long.toString()) }
    var appInit by remember { mutableStateOf(true) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            val coords = result.data
            val lat_result= coords?.getDoubleExtra("latitude", 0.0)
            val long_result= coords?.getDoubleExtra("longitude", 0.0)

            if(lat_result != null && long_result != null){
                latInput = lat_result.toString()
                longInput = long_result.toString()

                viewModel.updateLatitude(lat_result.toFloat())
                viewModel.updateLongitude(long_result.toFloat())
                viewModel.fetchWeather()
            }
        }
    }
    println("Raio do input: $latInput")

    LaunchedEffect(lat, long) {
        if((lat.toString() != latInput || long.toString() != longInput) && appInit){
            appInit = false
            latInput = lat.toString()
            longInput = long.toString()
        }
    }


    Card(
        colors = CardDefaults.cardColors(containerColor = colorScheme.primary),
        modifier = cardModifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)

            ) {
                Text(text = stringResource(
                    dam_A51706.coolweatherapptp3.R.string.coordinates),
                    color = colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = {
                    val intent = Intent(context, LocationPickerActivity::class.java)
                    launcher.launch(intent)
                },
                    modifier = Modifier.height(30.dp)
                ) {
                    Icon(Icons.Default.Public, contentDescription = "Search location")
                }
            }
            OutlinedTextField(
                value = latInput,
                singleLine = true,
                shape = shapes.large,
                onValueChange = {
                    latInput = it
                    onLatChange(it)
                },
                label = { Text(stringResource(dam_A51706.coolweatherapptp3.R.string.enter_latitude)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.secondary,
                    focusedLabelColor = colorScheme.secondary,
                    unfocusedBorderColor = colorScheme.onSecondary,
                    unfocusedLabelColor = colorScheme.onSecondary,
                    unfocusedTextColor = colorScheme.onPrimary,
                    cursorColor = colorScheme.onPrimary,
                ),
            )
            OutlinedTextField(
                value = longInput,
                singleLine = true,
                shape = shapes.large,
                onValueChange = {
                    longInput = it
                    onLogChange(it)
                },
                label = { Text(stringResource(dam_A51706.coolweatherapptp3.R.string.enter_longitude)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.secondary,
                    focusedLabelColor = colorScheme.secondary,
                    unfocusedBorderColor = colorScheme.onSecondary,
                    unfocusedLabelColor = colorScheme.onSecondary,
                    unfocusedTextColor = colorScheme.onPrimary,
                    cursorColor = colorScheme.onPrimary,
                ),
            )
        }
    }
}

@Composable
fun CoordinatesCardPreview (
    lat: Float,
    long: Float,
    onLatChange: (String) -> Unit,
    onLogChange: (String) -> Unit,
    cardModifier: Modifier = Modifier
        .wrapContentHeight(),
) {
    var latInput by remember{ mutableStateOf(lat.toString()) }
    var longInput by remember{ mutableStateOf(long.toString()) }
    var appInit by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val coords = result.data
            val lat_result= coords?.getDoubleExtra("latitude", 0.0)
            val long_result= coords?.getDoubleExtra("longitude", 0.0)

            if(lat_result != null && long_result != null){
                latInput = lat_result.toString()
                longInput = long_result.toString()
            }
        }
    }

    if((lat.toString() != latInput || long.toString() != longInput) && appInit){
        latInput = lat.toString()
        longInput = long.toString()
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = colorScheme.primary),
        modifier = cardModifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(
                    dam_A51706.coolweatherapptp3.R.string.coordinates),
                    color = colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = {

                },
                    modifier = Modifier.height(30.dp)
                ) {
                    Icon(Icons.Default.Public, contentDescription = "Search location")
                }
            }
            OutlinedTextField(
                value = latInput,
                singleLine = true,
                shape = shapes.large,
                onValueChange = {
                    latInput = it
                    onLatChange(it)
                },
                label = { Text(stringResource(dam_A51706.coolweatherapptp3.R.string.enter_latitude)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.secondary,
                    focusedLabelColor = colorScheme.secondary,
                    unfocusedBorderColor = colorScheme.onSecondary,
                    unfocusedLabelColor = colorScheme.onSecondary,
                    unfocusedTextColor = colorScheme.onPrimary,
                    cursorColor = colorScheme.onPrimary,
                ),
            )
            OutlinedTextField(
                value = longInput,
                singleLine = true,
                shape = shapes.large,
                onValueChange = {
                    longInput = it
                    onLogChange(it)
                },
                label = { Text(stringResource(dam_A51706.coolweatherapptp3.R.string.enter_longitude)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.secondary,
                    focusedLabelColor = colorScheme.secondary,
                    unfocusedBorderColor = colorScheme.onSecondary,
                    unfocusedLabelColor = colorScheme.onSecondary,
                    unfocusedTextColor = colorScheme.onPrimary,
                    cursorColor = colorScheme.onPrimary,
                ),
            )
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_3")
@Composable
fun CoordsScreenPreview() {
    WeatherAppTheme {
        CoordinatesCardPreview(0f , 0f, {}, {})
    }
}
