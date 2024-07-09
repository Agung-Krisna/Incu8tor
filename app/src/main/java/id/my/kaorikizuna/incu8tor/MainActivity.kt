package id.my.kaorikizuna.incu8tor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import id.my.kaorikizuna.incu8tor.ui.deviceDetail.DeviceDetailScreenPreview
import id.my.kaorikizuna.incu8tor.ui.theme.Incu8torTheme

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
                DeviceDetailScreenPreview()
            }
        }
    }
}
