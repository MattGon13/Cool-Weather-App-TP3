package dam_A51706.coolweatherapptp3.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dam_A51706.coolweatherapptp3.R
import dam_A51706.coolweatherapptp3.ui.theme.WeatherAppTheme

@Composable
fun LocationPickerScreen (onLocationSelected: (LatLng) -> Unit) {

    var selected by remember { mutableStateOf<LatLng?>(null) }
    val lisbon = LatLng(38.7167, -9.1333)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(lisbon, 5f)
    }

    Surface(color = MaterialTheme.colorScheme.surface){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp, vertical = 10.dp)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { coords ->
                    selected = coords
                }
            ){
                selected?.let {
                    Marker(
                        state = MarkerState(position = it)
                    )
                }
            }

            selected?.let{
                Button(
                    onClick = {onLocationSelected(it)},
                    modifier = Modifier.width(200.dp).height(50.dp).align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
                ) {
                    Text(stringResource(R.string.update))
                }
            }
        }
    }


}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,orientation=portrait")
@Composable
fun LocationScreenPreview() {
    WeatherAppTheme {
        LocationPickerScreen({})
    }
}