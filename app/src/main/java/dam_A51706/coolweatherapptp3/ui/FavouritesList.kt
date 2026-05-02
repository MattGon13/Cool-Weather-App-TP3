package dam_A51706.coolweatherapptp3.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dam_A51706.coolweatherapptp3.data.Favourite
import dam_A51706.coolweatherapptp3.ui.theme.WeatherAppTheme
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import io.ktor.websocket.Frame.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dam_A51706.coolweatherapptp3.R
import dam_A51706.coolweatherapptp3.viewmodel.WeatherViewModel


@Composable
fun FavouritesList(
    favourites: List<Favourite>,
    onSelect: (Favourite) -> Unit
){
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(favourites){ location ->
            Button(
                onClick = {onSelect(location)},
                modifier = Modifier.padding(horizontal = 3.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
            ) {
                Text(
                    text = location.locationName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }
        }
    }

}

@Composable
fun FavouritesSave(
    latitude: Float,
    longitude: Float,
    viewModel: WeatherViewModel
){
    var locationName by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ){
        OutlinedTextField(
            value = locationName,
            singleLine = true,
            shape = shapes.large,
            onValueChange = {
                locationName = it
            },
            label = { Text(stringResource(R.string.location_name)) },
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 5.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.secondary,
                focusedLabelColor = colorScheme.secondary,
                unfocusedBorderColor = colorScheme.onSecondary,
                unfocusedLabelColor = colorScheme.onSecondary,
                unfocusedTextColor = colorScheme.onPrimary,
                cursorColor = colorScheme.onPrimary,
            ),
        )
        Button(
            onClick = {
                if(locationName.isNotBlank()){
                    val newFavourite = Favourite(locationName, latitude, longitude)
                    viewModel.addFavourite(newFavourite)
                    locationName = ""
                }
            },
            modifier = Modifier.padding(horizontal = 5.dp).weight(0.6F),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
        ){
            Text(
                text = stringResource(R.string.add),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun FavouritesSavePreview(
    latitude: Float,
    longitude: Float
){
    var locationName by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ){
        OutlinedTextField(
            value = locationName,
            singleLine = true,
            shape = shapes.large,
            onValueChange = {
                locationName = it
            },
            label = { Text(stringResource(R.string.location_name)) },
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 5.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.secondary,
                focusedLabelColor = colorScheme.secondary,
                unfocusedBorderColor = colorScheme.onSecondary,
                unfocusedLabelColor = colorScheme.onSecondary,
                unfocusedTextColor = colorScheme.onPrimary,
                cursorColor = colorScheme.onPrimary,
            ),
        )
        Button(
            onClick = {},
            modifier = Modifier.padding(horizontal = 5.dp).weight(0.6F),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
        ){
            Text(
                text = stringResource(R.string.add),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_3")
@Composable
fun FavouritesPreview() {
    WeatherAppTheme {
        Surface(color = MaterialTheme.colorScheme.primary) {
            FavouritesSavePreview(20f, 50f)
        }
    }
}