package id.my.kaorikizuna.incu8tor.ui.home

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.my.kaorikizuna.incu8tor.R
import id.my.kaorikizuna.incu8tor.ui.components.Incu8torSearchBar
import id.my.kaorikizuna.incu8tor.ui.components.onSearchClicked


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
    Scaffold(topBar = {
        Incu8torSearchBar(::onSearchClicked)
    }) { innerPadding ->
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceCard(device: Device) {
    OutlinedCard(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 10.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(device.name, style = MaterialTheme.typography.titleMedium)
                Text(device.macAddress, style = MaterialTheme.typography.bodySmall)
            }
            // the icon needs to be within  a box to be able to be centered vertically
            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    painter = if (device.isConnected) painterResource(id = R.drawable.wifi)
                    else painterResource(id = R.drawable.wifi_off),
                    contentDescription = "Device Connected",
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DeviceCardPreview() {
    DeviceCard(device = devices[0])
}