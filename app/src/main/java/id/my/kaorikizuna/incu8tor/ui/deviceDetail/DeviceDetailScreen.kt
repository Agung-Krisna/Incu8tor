package id.my.kaorikizuna.incu8tor.ui.deviceDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.my.kaorikizuna.incu8tor.R
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.ui.theme.Incu8torTheme
import id.my.kaorikizuna.incu8tor.viewmodel.DeviceViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.ColorFilter
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import androidx.compose.material3.OutlinedCard as OutlinedCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailScreen(
    deviceDetail: DeviceDetail,
    onBackClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onIncubationClicked: (DeviceDetail) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = deviceDetail.deviceName,
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onSettingsClicked() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_setting),
                            contentDescription = "Settings"
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedCard(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(100.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_temperature),
                                contentDescription = "Temperature",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${deviceDetail.sensors.temperature} °C",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    OutlinedCard(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(100.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_humidity),
                                contentDescription = "Humidity",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${deviceDetail.sensors.humidity} %",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Start Date", style = MaterialTheme.typography.titleSmall)
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(getDateFromEpoch(deviceDetail.dayStart))
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Egg Date", style = MaterialTheme.typography.titleSmall)
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val daysBetween = calculateDaysBetween(deviceDetail.dayStart)
                            Text("$daysBetween days")
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onIncubationClicked(deviceDetail) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    if (deviceDetail.dayStart == 0L) Text("Start Incubation") else Text("End Incubation")
                }
            }
        }
    )
}

fun calculateDaysBetween(startDateEpoch: Long): Long {
    if (startDateEpoch == 0L) {
        return 0
    }
    val currentDate = LocalDate.now()
    val startDate = LocalDate.ofEpochDay(startDateEpoch / (24 * 60 * 60))
    return ChronoUnit.DAYS.between(startDate, currentDate)
}

fun getDateFromEpoch(epochTime: Long): String {
    if (epochTime == 0L) {
        return "Not Yet Started"
    }
    return SimpleDateFormat(
        "dd MMMM yyyy, HH:mm",
        Locale.getDefault()
    ).format(Date(epochTime * 1000))

}