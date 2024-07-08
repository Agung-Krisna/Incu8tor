package id.my.kaorikizuna.incu8tor.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.my.kaorikizuna.incu8tor.R
import id.my.kaorikizuna.incu8tor.model.Device
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.ui.components.Incu8torSearchBar
import id.my.kaorikizuna.incu8tor.ui.components.onSearchClicked
import id.my.kaorikizuna.incu8tor.viewmodel.DeviceViewModel

// dummy devices
val devices = listOf(
    Device(
        name = "Incubator Kandang Timur", macAddress = "77:63:74:8B:62:E8", isConnected = true
    ),
    Device(
        name = "Incubator Kandang Barat", macAddress = "41:00:FB:64:82:BA", isConnected = false
    ),
)

@Composable
fun HomeScreen() {
    val viewModel = DeviceViewModel()

//  turns out that the second positional argument of remember is the setter function,
    val (devices, setDevices) = remember { mutableStateOf(emptyList<DeviceDetail>()) }
    viewModel.getAllDevices(onSuccess = {
        setDevices(it)
        Log.d("devices", "${devices.toString()} ${it.size}")
    })

    Scaffold(topBar = {
        Incu8torSearchBar(::onSearchClicked)
    }, floatingActionButton = {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "Add Device"
            )
        }
    }) { innerPadding ->
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(devices) { device ->
                    DeviceCard(device)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceCard(deviceDetail: DeviceDetail) {
    OutlinedCard(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 10.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(deviceDetail.deviceName, style = MaterialTheme.typography.titleMedium)
                Text(deviceDetail.macAddress, style = MaterialTheme.typography.bodySmall)
            }
//            TODO
            // the icon needs to be within a container (box or row) to be able to be centered vertically
//            Row(modifier = Modifier.align(Alignment.CenterVertically)) {
//                Icon(
//                    painter = if (deviceDetail.isConnected) painterResource(id = R.drawable.wifi)
//                    else painterResource(id = R.drawable.wifi_off),
//                    contentDescription = "Device Connected",
//                )
//            }
        }
    }
}
