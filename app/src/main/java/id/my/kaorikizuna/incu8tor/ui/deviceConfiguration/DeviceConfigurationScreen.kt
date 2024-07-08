package id.my.kaorikizuna.incu8tor.ui.deviceConfiguration

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.ui.components.Incu8torModifiableTopBar
import id.my.kaorikizuna.incu8tor.ui.theme.Blue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkBlue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkRed
import id.my.kaorikizuna.incu8tor.ui.theme.Red
import id.my.kaorikizuna.incu8tor.viewmodel.DeviceViewModel

@Composable
fun DeviceConfigurationScreen(deviceDetail: DeviceDetail) {
    val (currentDeviceDetail, setCurrentDeviceDetail) = remember { mutableStateOf(deviceDetail) }
//    HAHA HA GOT 'EM
//    if (currentDeviceDetail == DeviceDetail()) {
//        setCurrentDeviceDetail(deviceDetail)
//    }
    LaunchedEffect(deviceDetail) {
        if (currentDeviceDetail == DeviceDetail()) {
            setCurrentDeviceDetail(deviceDetail)
        }
    }


    Scaffold(topBar = {
        Incu8torModifiableTopBar(
            deviceTitle = deviceDetail.deviceName,
            actionButton = { DeleteActionButton() })
    }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
////        TODO: create an empty text validation
            OutlinedTextField(
                value = currentDeviceDetail.deviceName,
                onValueChange = {
                    setCurrentDeviceDetail(currentDeviceDetail.copy(deviceName = it))
                },
                label = { Text("Change Device Name") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                singleLine = true
            )

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
                ElevatedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Update")
                }
            }

        }
    }
}

@Composable
fun DeleteActionButton() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Filled.Delete, contentDescription = "Delete"
        )
    }
}


@Preview
@Composable
fun DeviceConfigurationScreenPreview() {
    val viewModel = DeviceViewModel()
    val (deviceDetail, setDeviceDetail) = remember { mutableStateOf(DeviceDetail()) }
    LaunchedEffect(Unit) {
        viewModel.getDevice("24:DC:C3:45:EA:CC", onSuccess = {
            setDeviceDetail(it)
        })
    }
    if (deviceDetail != DeviceDetail()) {
        DeviceConfigurationScreen(deviceDetail)
    }
}