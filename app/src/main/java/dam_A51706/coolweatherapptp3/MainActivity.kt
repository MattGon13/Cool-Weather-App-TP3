package dam_A51706.coolweatherapptp3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dam_A51706.coolweatherapptp3.ui.WeatherUI
import dam_A51706.coolweatherapptp3.ui.theme.WeatherAppTheme
import dam_A51706.coolweatherapptp3.viewmodel.WeatherViewModel
import kotlin.getValue
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme{
                WeatherUI(weatherViewModel = viewModel)
            }
        }
        getLocation()
    }

    fun getLocation(): MutableList<String>{
        val fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        val latlogValues = mutableListOf<String>()

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            val locationManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location = task.getResult()
                    if(location != null){
                        val lat = location.latitude.toFloat()
                        val log = location.longitude.toFloat()

                        viewModel.updateLatitude(lat)
                        viewModel.updateLongitude(log)

                        viewModel.fetchWeather()
                        println("State: lat- ${viewModel.weatherUIState.value.latitude}")
                    }
                }
            }
            else{
                Toast.makeText(this, getString(R.string.please_turn_location_on), Toast.LENGTH_LONG).show()
                val intent: Intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        return latlogValues
    }
}
