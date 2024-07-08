package id.my.kaorikizuna.incu8tor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import id.my.kaorikizuna.incu8tor.ui.detail.DeviceDetailScreen
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
//                    viewModel.getAllDevices(onSuccess = {}, context = LocalContext.current)
//                HomeScreen()
                DeviceDetailScreen(deviceDetail = ) {

                }
            }
        }
    }
}
