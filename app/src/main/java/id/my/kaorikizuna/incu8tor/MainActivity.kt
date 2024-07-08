package id.my.kaorikizuna.incu8tor

import android.bluetooth.BluetoothClass.Device
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import id.my.kaorikizuna.incu8tor.ui.deviceConfiguration.DeviceConfigurationScreen
import id.my.kaorikizuna.incu8tor.ui.deviceConfiguration.DeviceConfigurationScreenPreview
import id.my.kaorikizuna.incu8tor.ui.home.HomeScreen
import id.my.kaorikizuna.incu8tor.ui.theme.Incu8torTheme
import id.my.kaorikizuna.incu8tor.viewmodel.DeviceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Incu8torTheme {
//                val viewModel = DeviceViewModel()
//                val deviceDetail = viewModel.getDevice("24:DC:C3:45:EA:CC")
//                Log.d(TAG, "device: ${deviceDetail.toString()}")
//                DeviceConfigurationScreen(deviceDetail)
                DeviceConfigurationScreenPreview()
            }
        }
    }
}
