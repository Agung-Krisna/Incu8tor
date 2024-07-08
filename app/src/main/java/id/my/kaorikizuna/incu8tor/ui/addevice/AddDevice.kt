package id.my.kaorikizuna.incu8tor.ui.addevice

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddDeviceScreen(onSave: () -> Unit) {
    var deviceName by remember { mutableStateOf(TextFieldValue("")) }
    var macAddress by remember { mutableStateOf(TextFieldValue("")) }
    var temperature by remember { mutableStateOf(0f) }
    var humidity by remember { mutableStateOf(0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incu8tor") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = deviceName,
                onValueChange = { deviceName = it },
                label = { Text("Input Device Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = macAddress,
                onValueChange = { macAddress = it },
                label = { Text("Input MAC Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Input Temperature")
            Slider(
                value = temperature,
                onValueChange = { temperature = it },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Input Humidity")
            Slider(
                value = humidity,
                onValueChange = { humidity = it },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onSave) {
                Text("Save")
            }
        }
    }
}

@Composable
fun OutlinedTextField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, label: () -> Unit, modifier: Modifier) {

}

@Composable
fun TopAppBar(title: @Composable () -> Unit) {

}

@Composable
fun Scaffold(topBar: @Composable () -> Unit, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun AddDeviceScreenPreview() {
    AddDeviceScreen(onSave = {})
}