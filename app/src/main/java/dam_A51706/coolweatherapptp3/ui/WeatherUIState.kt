package dam_A51706.coolweatherapptp3.ui

data class WeatherUIState(
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val temperature: Float = 0f,
    val windSpeed: Float = 0f,
    val windDirection: Int = 0,
    val weatherCode: Int = 0,
    val seaLevelPressure: Float = 0f,
    val time: String = ""
)
