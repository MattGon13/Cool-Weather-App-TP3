package dam_A51706.coolweatherapptp3.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WeatherInfoRow (
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(horizontal = 5.dp)
    ) {
        Text(text = name,
            color = colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1F)
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.5F),
            textAlign = TextAlign.End
        )
    }
}