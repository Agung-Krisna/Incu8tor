package id.my.kaorikizuna.incu8tor

import android.bluetooth.BluetoothClass.Device
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.ui.addevice.AddDeviceScreen
import id.my.kaorikizuna.incu8tor.ui.deviceConfiguration.DeviceConfigurationScreen
import id.my.kaorikizuna.incu8tor.ui.deviceDetail.DeviceDetailScreen
import id.my.kaorikizuna.incu8tor.ui.home.HomeScreen
import id.my.kaorikizuna.incu8tor.ui.theme.Incu8torTheme
import id.my.kaorikizuna.incu8tor.viewmodel.DeviceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Incu8torTheme {
                val viewModel = DeviceViewModel()
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable(route = "home") {
                        HomeScreen(
                            onDeviceClicked = { deviceDetail -> navController.navigate("deviceDetail/${deviceDetail.macAddress}") },
                            navController = navController
                        )
                    }
                    composable(route = "addDevice") {
                        AddDeviceScreen(
                            onBackClicked = { navController.navigate("home") },
                            onSave = { deviceDetail ->
                                viewModel.addDevice(deviceDetail)
                            })
                    }
                    composable(route = "deviceConfiguration/{macAddress}") { backStackEntry ->
                        val macAddress = backStackEntry.arguments?.getString("macAddress")
                        val (deviceDetail, setDeviceDetail) = remember { mutableStateOf(DeviceDetail()) }
                        LaunchedEffect(Unit) {
                            if (macAddress != null) {
                                viewModel.getDevice(macAddress, onSuccess = {
                                    setDeviceDetail(it)
                                })
                            }
                        }
                        if (deviceDetail != DeviceDetail()) {
                            DeviceConfigurationScreen(deviceDetail = deviceDetail,
                                onUpdate = { deviceDetail ->
                                    viewModel.updateDevice(deviceDetail)
                                },
                                onDelete = { deviceDetail ->
                                    viewModel.deleteDevice(deviceDetail)
                                    navController.navigate("home")
                                },
                                onBackClicked = {
                                    navController.navigate("deviceDetail/${deviceDetail.macAddress}")
                                })
                        }
                    }
                    composable(route = "deviceDetail/{macAddress}") { backStackEntry ->
                        val macAddress = backStackEntry.arguments?.getString("macAddress")
                        val (deviceDetail, setDeviceDetail) = remember { mutableStateOf(DeviceDetail()) }
                        LaunchedEffect(Unit) {
                            if (macAddress != null) {
                                viewModel.getDevice(macAddress, onSuccess = {
                                    setDeviceDetail(it)
                                })
                            }
                        }
                        if (deviceDetail != DeviceDetail()) {
                            DeviceDetailScreen(deviceDetail = deviceDetail,
                                onBackClicked = { navController.navigate("home") },
                                onSettingsClicked = { navController.navigate("deviceConfiguration/${deviceDetail.macAddress}") },
                                onIncubationClicked = { deviceDetail ->
                                    // if incubation has already begun, end incubation
                                    if (deviceDetail.dayStart > 0) {
                                        viewModel.updateDevice(deviceDetail.copy(dayStart = 0))
                                    // else, start the incubation
                                    } else {
                                        viewModel.updateDevice(deviceDetail.copy(dayStart = System.currentTimeMillis() / 1000))
                                    }
                                    navController.navigate("home")
                                })
                        }
                    }

                }
            }
        }
    }
}
