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
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.ui.addevice.AddDeviceScreen
import id.my.kaorikizuna.incu8tor.ui.deviceConfiguration.DeviceConfigurationScreen
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
//                val deviceDetail = viewModel.getDevice("24:DC:C3:45:EA:CC")
//                Log.d(TAG, "device: ${deviceDetail.toString()}")
//                DeviceConfigurationScreenPreview()
//                HomeScreen()

                // testing purposes
                val (deviceDetail, setDeviceDetail) = remember { mutableStateOf(DeviceDetail()) }
                LaunchedEffect(Unit) {
                    viewModel.getDevice("24:DC:C3:45:EA:CC", onSuccess = {
                        setDeviceDetail(it)
                    })
                }
                if (deviceDetail != DeviceDetail()) {
                    DeviceConfigurationScreen(
                        deviceDetail = deviceDetail,
                        onUpdate = { deviceDetail -> viewModel.updateDevice(deviceDetail) },
                        onDelete = { deviceDetail -> viewModel.deleteDevice(deviceDetail) })
                }

//                AddDeviceScreen(onSave = { deviceDetail ->
//                    viewModel.addDevice(deviceDetail)
//                })
            }
        }
    }
}
