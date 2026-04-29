package dam_A51706.coolweatherapptp3.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
) {
    var latInput by remember{ mutableStateOf(lat.toString()) }
    var longInput by remember{ mutableStateOf(long.toString()) }
    var appInit by remember { mutableStateOf(true) }

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
                Icon(Icons.Default.Public, contentDescription = "Search location")
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
        CoordinatesCard(0f , 0f, {}, {})
    }
}
