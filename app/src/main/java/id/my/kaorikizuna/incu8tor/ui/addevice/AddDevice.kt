package id.my.kaorikizuna.incu8tor.ui.addevice

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.ui.theme.Blue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkBlue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkRed
import id.my.kaorikizuna.incu8tor.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeviceScreen(onSave: (DeviceDetail) -> Unit) {
//    var deviceName by remember { mutableStateOf(TextFieldValue("")) }
//    var macAddress by remember { mutableStateOf(TextFieldValue("")) }
//    var temperature by remember { mutableStateOf(0f) }
//    var humidity by remember { mutableStateOf(0f) }

    val (deviceDetail, setDeviceDetail) = remember { mutableStateOf(DeviceDetail()) }
    val (currentDeviceDetail, setCurrentDeviceDetail) = remember { mutableStateOf(deviceDetail) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incu8tor") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = deviceDetail.deviceName,
                onValueChange = { it ->
                    setDeviceDetail(deviceDetail.copy(deviceName = it ))
                },
                label = { Text("Input Device Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = deviceDetail.macAddress,
                onValueChange = { it ->
                    setDeviceDetail(deviceDetail.copy(macAddress = it))
                },
                label = { Text("Input MAC Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            var temperatureSliderPositions by remember { mutableStateOf(currentDeviceDetail.settings.temperature.min.toFloat()..currentDeviceDetail.settings.temperature.max.toFloat()) }
            Column {
                Text("Temperature")
                RangeSlider(
                    value = temperatureSliderPositions,
                    onValueChange = { temperatureSliderPositions = it },
                    valueRange = 20f..50f,
                    colors = SliderDefaults.colors(
                        inactiveTrackColor = DarkRed,
                        activeTrackColor = Red
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Min: ${temperatureSliderPositions.start.toInt()}°C",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        "Max: ${temperatureSliderPositions.endInclusive.toInt()}°C",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            var humiditySliderPositions by remember { mutableStateOf(deviceDetail.settings.humidity.min.toFloat()..deviceDetail.settings.humidity.max.toFloat()) }
            Column {
                Text("Humidity")
                RangeSlider(
                    value = humiditySliderPositions,
                    onValueChange = { humiditySliderPositions = it },
                    valueRange = 30f..80f,
                    colors = SliderDefaults.colors(
                        inactiveTrackColor = DarkBlue,
                        activeTrackColor = Blue
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Min: ${humiditySliderPositions.start.toInt()}%",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        "Max: ${humiditySliderPositions.endInclusive.toInt()}%",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedButton(onClick = { onSave(deviceDetail) }) {
                    Text(text = "Save")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddDeviceScreenPreview() {
    AddDeviceScreen(onSave = {})
}
