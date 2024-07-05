//package id.my.kaorikizuna.incu8tor.ui.deviceConfiguration
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Slider
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import id.my.kaorikizuna.incu8tor.model.DeviceDetail
//import id.my.kaorikizuna.incu8tor.repository.writeData
//import id.my.kaorikizuna.incu8tor.ui.components.Incu8torModifiableTopBar
//import id.my.kaorikizuna.incu8tor.viewmodel.DeviceConfigurationViewModel
//
////val deviceDetail: DeviceDetail = DeviceDetail(
////    name = "Incubator Kandang Timur",
////    macAddress = "77:63:74:8B:62:E8",
////    isConnected = true,
////    minTemperature = 20,
////    maxTemperature = 30,
////    minHumidity = 60,
////    maxHumidity = 80
////)
//
//@Composable
//fun DeviceConfigurationScreen(deviceDetail: DeviceDetail) {
////    var deviceName by remember { mutableStateOf(deviceDetail.name) }
////    var macAddress by remember { mutableStateOf(deviceDetail.macAddress) }
////    var isConnected by remember { mutableStateOf(deviceDetail.isConnected) }
////    var minTemperature by remember { mutableIntStateOf(deviceDetail.minTemperature) }
////    var maxTemperature by remember { mutableIntStateOf(deviceDetail.maxTemperature) }
////    var minHumidity by remember { mutableIntStateOf(deviceDetail.minHumidity) }
////    var maxHumidity by remember { mutableIntStateOf(deviceDetail.maxHumidity) }
//
////    val devices by viewModel.items.collectAsState()
//
//    Scaffold(topBar = {
//        Incu8torModifiableTopBar(deviceTitle = deviceDetail.name,
//            actionButton = { DeleteActionButton() })
//    }) { paddingValues ->
//        Column(modifier = Modifier.padding(paddingValues)) {
////          TODO: create an empty text validation
////            TextField(
////                value = deviceName,
////                onValueChange = { deviceName = it },
////                label = { Text("Change Device Name") },
////                modifier = Modifier.fillMaxWidth(),
////                maxLines = 1,
////                singleLine = true
////            )
//
////            Text(text = "Min Temperature: ${minTemperature.toInt()}")
////            Slider(
////                value = minTemperature,
////                onValueChange = { value ->
////                    minTemperature = value
////                    textFieldValue = TextFieldValue(value.toInt().toString())
////                    onValueChange(value)
////                },
////                valueRange = valueRange,
////                steps = (valueRange.endInclusive - valueRange.start).toInt() - 1,
////                modifier = Modifier.fillMaxWidth()
////            )
////            OutlinedTextField(
////                value = textFieldValue,
////                onValueChange = { newValue ->
////                    val newValueFloat = newValue.text.toFloatOrNull()
////                    if (newValueFloat != null && newValueFloat in valueRange) {
////                        sliderValue = newValueFloat
////                        textFieldValue = newValue
////                        onValueChange(newValueFloat)
////                    }
////                },
////                label = { Text("$label Value") },
////                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
////                modifier = Modifier.fillMaxWidth()
////            )
//
//        }
//    }
//}
//
//
//
//@Composable
//fun DeleteActionButton() {
//    IconButton(onClick = { /*TODO*/ }) {
//        Icon(
//            imageVector = Icons.Filled.Delete, contentDescription = "Delete"
//        )
//    }
//}
//
//@Preview
//@Composable
//fun DeviceConfigurationScreenPreview() {
//
//    DeviceConfigurationScreen(deviceDetail)
//}