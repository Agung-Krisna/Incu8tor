package id.my.kaorikizuna.incu8tor.ui.deviceConfiguration

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.model.DeviceSettings
import id.my.kaorikizuna.incu8tor.model.Humidity
import id.my.kaorikizuna.incu8tor.model.Temperature
import id.my.kaorikizuna.incu8tor.ui.components.Incu8torModifiableTopBar
import id.my.kaorikizuna.incu8tor.ui.theme.Blue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkBlue
import id.my.kaorikizuna.incu8tor.ui.theme.DarkRed
import id.my.kaorikizuna.incu8tor.ui.theme.Red
import id.my.kaorikizuna.incu8tor.viewmodel.DeviceViewModel

@Composable
fun DeviceConfigurationScreen(
    deviceDetail: DeviceDetail,
    onUpdate: (DeviceDetail) -> Unit,
    onDelete: (DeviceDetail) -> Unit,
    onBackClicked: () -> Unit
) {
    val (currentDeviceDetail, setCurrentDeviceDetail) = remember { mutableStateOf(deviceDetail) }
    var showDialog by remember { mutableStateOf(false) }
//  HAHA HA GOT 'EM
    LaunchedEffect(deviceDetail) {
        if (currentDeviceDetail == DeviceDetail()) {
            setCurrentDeviceDetail(deviceDetail)
        }
    }

    // state hoisting, show dialog only when showDialog is true
    if (showDialog) {
        DeleteDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                onDelete(deviceDetail)
            })
    }


    Scaffold(topBar = {
        Incu8torModifiableTopBar(
            deviceTitle = deviceDetail.deviceName,
            actionButton = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.Delete, contentDescription = "Delete"
                    )
                }
            },
            backNavigation = onBackClicked
        )
    })
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(value = currentDeviceDetail.deviceName,
                onValueChange = {
                    setCurrentDeviceDetail(currentDeviceDetail.copy(deviceName = it))
                },
                label = { Text("Change Device Name") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                singleLine = true,
                supportingText = {
                    AnimatedTextError(
                        visible = currentDeviceDetail.deviceName.isEmpty(),
                        text = "Device Name cannot be empty!"
                    )
                })

            var temperatureSliderPositions by remember { mutableStateOf(currentDeviceDetail.settings.temperature.min.toFloat()..currentDeviceDetail.settings.temperature.max.toFloat()) }
            Column {
                Text("Temperature")
                RangeSlider(
                    value = temperatureSliderPositions, onValueChange = { it ->
                        temperatureSliderPositions = it

                        // set the temperature while using the humidity from the currently saved result
                        setCurrentDeviceDetail(
                            currentDeviceDetail.copy(
                                settings = DeviceSettings(
                                    temperature = Temperature(
                                        temperatureSliderPositions.start.toInt(),
                                        temperatureSliderPositions.endInclusive.toInt()
                                    ), humidity = currentDeviceDetail.settings.humidity
                                )
                            )
                        )
                    }, valueRange = 30f..40f, colors = SliderDefaults.colors(
                        inactiveTrackColor = DarkRed, activeTrackColor = Red
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
                    value = humiditySliderPositions, onValueChange = { it ->
                        humiditySliderPositions = it

                        // set the humidity while keeping the temperature from the saved result
                        setCurrentDeviceDetail(
                            currentDeviceDetail.copy(
                                settings = DeviceSettings(
                                    temperature = currentDeviceDetail.settings.temperature,
                                    humidity = Humidity(
                                        humiditySliderPositions.start.toInt(),
                                        humiditySliderPositions.endInclusive.toInt()
                                    )
                                )
                            )
                        )
                    }, valueRange = 30f..80f, colors = SliderDefaults.colors(
                        inactiveTrackColor = DarkBlue, activeTrackColor = Blue
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
                    onUpdate(currentDeviceDetail)
                    onBackClicked()
                }) {
                    Text(
                        text = "Update",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedTextError(visible: Boolean, text: String) {
    val density = LocalDensity.current
    AnimatedVisibility(visible = visible, enter = slideInVertically {
        with(density) { -10.dp.roundToPx() }
    } + fadeIn(initialAlpha = 0.3f), exit = slideOutVertically() + fadeOut()) {
        Text(
            text = text, color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun DeleteDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(icon = {
        Icon(Icons.Filled.Delete, contentDescription = "Delete Device")
    }, onDismissRequest = onDismiss, title = {
        Text(text = "Delete Device")
    }, text = {
        Text(text = "Are you sure you want to delete this device?")
    }, confirmButton = {
        TextButton(onClick = onConfirm) {
            Text("Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
    })
}


//@Preview
//@Composable
//fun DeviceConfigurationScreenPreview() {
//    val viewModel = DeviceViewModel()
//    val (deviceDetail, setDeviceDetail) = remember { mutableStateOf(DeviceDetail()) }
//    LaunchedEffect(Unit) {
//        viewModel.getDevice("24:DC:C3:45:EA:CC", onSuccess = {
//            setDeviceDetail(it)
//        })
//    }
//    if (deviceDetail != DeviceDetail()) {
//        DeviceConfigurationScreen(deviceDetail)
//    }
//}