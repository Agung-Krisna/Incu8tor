package id.my.kaorikizuna.incu8tor.ui.addevice

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton

import androidx.compose.material3.IconButton

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderColors

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import id.my.kaorikizuna.incu8tor.ui.components.Incu8torModifiableTopBar
import id.my.kaorikizuna.incu8tor.ui.theme.Blue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkBlue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkRed
import id.my.kaorikizuna.incu8tor.ui.theme.Red
import id.my.kaorikizuna.incu8tor.viewmodel.DeviceViewModel
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

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.model.DeviceSettings
import id.my.kaorikizuna.incu8tor.model.Humidity
import id.my.kaorikizuna.incu8tor.model.Temperature
import id.my.kaorikizuna.incu8tor.ui.theme.Blue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkBlue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkRed
import id.my.kaorikizuna.incu8tor.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeviceScreen(onSave: (DeviceDetail) -> Unit, onBackClicked: () -> Unit) {

    val (deviceDetail, setDeviceDetail) = remember { mutableStateOf(DeviceDetail()) }
    Scaffold(
        topBar = {
            Incu8torModifiableTopBar(
                deviceTitle = "Incu8tor",
                actionButton = {},
                backNavigation = { onBackClicked() })
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
                onValueChange = {
                    setDeviceDetail(deviceDetail.copy(deviceName = it))
                },
                label = { Text("Input Device Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = deviceDetail.macAddress,
                onValueChange = {
                    setDeviceDetail(deviceDetail.copy(macAddress = it))
                },
                label = { Text("Input MAC Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // set the device to be in valid range
            deviceDetail.settings =
                DeviceSettings(temperature = Temperature(34, 38), humidity = Humidity(50, 70))

            var temperatureSliderPositions by remember { mutableStateOf(deviceDetail.settings.temperature.min.toFloat()..deviceDetail.settings.temperature.max.toFloat()) }
            Column {
                Text("Temperature")
                RangeSlider(
                    value = temperatureSliderPositions,
                    onValueChange = {
                        temperatureSliderPositions = it

                        setDeviceDetail(
                            deviceDetail.copy(
                                settings = DeviceSettings(
                                    temperature = Temperature(
                                        temperatureSliderPositions.start.toInt(),
                                        temperatureSliderPositions.endInclusive.toInt()
                                    ),
                                    humidity = deviceDetail.settings.humidity
                                )
                            )
                        )
//                        temperatureSliderPositions = it
                    },
                    valueRange = 30f..40f,
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
                    onValueChange = {
                        humiditySliderPositions = it
                        setDeviceDetail(
                            deviceDetail.copy(
                                settings = DeviceSettings(
                                    temperature = deviceDetail.settings.temperature,
                                    humidity = Humidity(
                                        humiditySliderPositions.start.toInt(),
                                        humiditySliderPositions.endInclusive.toInt()
                                    )
                                )
                            )
                        )
                    },
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
                Button(onClick = {
                    Log.w("asdfasdf", "$deviceDetail")
                    onSave(deviceDetail)
                    onBackClicked()
                }) {
                    Text(text = "Save")
                }
            }

        }
    }
}