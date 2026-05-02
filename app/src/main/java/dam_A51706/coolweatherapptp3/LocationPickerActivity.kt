package dam_A51706.coolweatherapptp3

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dam_A51706.coolweatherapptp3.ui.LocationPickerScreen
import dam_A51706.coolweatherapptp3.ui.theme.WeatherAppTheme

class LocationPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                LocationPickerScreen { latLng ->
                    val result = Intent().apply {
                        putExtra("latitude", latLng.latitude)
                        putExtra("longitude", latLng.longitude)
                    }
                    setResult(RESULT_OK, result)
                    finish()
                }
            }
        }
    }
}