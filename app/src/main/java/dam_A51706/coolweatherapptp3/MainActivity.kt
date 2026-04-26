package dam_A51706.coolweatherapptp3

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dam_A51706.coolweatherapptp3.ui.WeatherUI
import dam_A51706.coolweatherapptp3.viewmodel.WeatherViewModel
import kotlin.getValue


class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherUI(weatherViewModel = viewModel)
        }
    }
}
