package id.my.kaorikizuna.incu8tor.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.my.kaorikizuna.incu8tor.R
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.ui.components.Incu8torSearchBar
import id.my.kaorikizuna.incu8tor.ui.components.ErrorToast
import id.my.kaorikizuna.incu8tor.viewmodel.DeviceViewModel


@Composable
fun HomeScreen(navController: NavController, onDeviceClicked: (DeviceDetail) -> Unit) {
    val viewModel = DeviceViewModel()
//  turns out that the second positional argument of remember is the setter function,
    val (devices, setDevices) = remember { mutableStateOf(emptyList<DeviceDetail>()) }
    val showSearchedDevices = remember { mutableStateOf(false) }

    // declaring error toast
    val errorToast = remember { ErrorToast() }

    // applying the local context
    errorToast.apply {
        context = LocalContext.current
    }

    Scaffold(topBar = {
        Incu8torSearchBar(onSearchInitiated = { query ->
            viewModel.searchDevices(query,
                onSuccess = { devices ->
                    setDevices(devices)
                    Log.d("device", "$devices")
                },
                onFailure = { exception -> errorToast.show(exception.message.toString()) })
        },
            // on search, empty the devices first
            onSearchClicked = {
                showSearchedDevices.value = true
                setDevices(emptyList())
            },
            // on back, repopulate the devices
            onBackClicked = {
                showSearchedDevices.value = false
            })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate("addDevice") },
            modifier = Modifier.offset(x = (-25).dp, y = (-30).dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "Add Device"
            )
        }
    }) { innerPadding ->
        Column {
            if (!showSearchedDevices.value) {
                viewModel.getAllDevices(onSuccess = {
                    setDevices(it)
                    Log.d("devices", "$devices ${it.size}")
                }, onFailure = { exception ->
                    errorToast.show(exception.message.toString())
                })
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(devices) { device ->
                    DeviceCard(device, onClick = { onDeviceClicked(device) })
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceCard(deviceDetail: DeviceDetail, onClick: () -> Unit) {
    OutlinedCard(onClick = onClick, modifier = Modifier.padding(horizontal = 10.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = deviceDetail.deviceName, style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = deviceDetail.macAddress, style = MaterialTheme.typography.bodySmall
                )
            }
            // the icon needs to be within a container (box or row) to be able to be centered vertically
            // device is online if it can report temperature and humidity
            Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    painter = if (deviceDetail.sensors.humidity > 0 && deviceDetail.sensors.temperature > 0) painterResource(
                        id = R.drawable.wifi
                    )
                    else painterResource(id = R.drawable.wifi_off),
                    contentDescription = "Device Connected",
                )
            }
        }
    }
}
