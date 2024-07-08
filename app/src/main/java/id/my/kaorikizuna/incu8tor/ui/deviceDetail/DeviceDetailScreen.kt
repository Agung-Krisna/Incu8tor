package id.my.kaorikizuna.incu8tor.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.my.kaorikizuna.incu8tor.R
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.ui.theme.Incu8torTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailScreen(deviceDetail: DeviceDetail, onBackClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Incu8tor") },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_temperature),
                        contentDescription = "Temperature"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Temperature: ${deviceDetail.sensors.temperature} °C",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_humidity),
                        contentDescription = "Humidity"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Humidity: ${deviceDetail.sensors.humidity} %",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = "Start Date: ${deviceDetail.dayStart}",  // Format sesuai dengan kebutuhan
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Egg Date: ${deviceDetail.settings.temperature.max}",  // Contoh, ganti dengan field yang sesuai dari DeviceDetail
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* TODO: Implement action */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "End Incubation")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDeviceDetailScreen() {
    Incu8torTheme {
        DeviceDetailScreen(
            deviceDetail = DeviceDetail(),  // Ganti dengan instance DeviceDetail yang sebenarnya
            onBackClicked = { /* Preview action */ }
        )
    }
}
