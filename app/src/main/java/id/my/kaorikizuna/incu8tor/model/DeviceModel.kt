package id.my.kaorikizuna.incu8tor.model

import androidx.compose.runtime.Updater

data class Device(var name: String, var macAddress: String, var isConnected: Boolean)

data class Humidity(
    var min: Int,
    var max: Int
) {
    constructor() : this(0, 0)
}

data class Temperature(
    var min: Int,
    var max: Int
) {
    constructor() : this(0, 0)
}

// DeviceSettings from the app
data class DeviceSettings(
    var humidity: Humidity,
    var temperature: Temperature
) {
    constructor() : this(Humidity(0, 0), Temperature(0, 0))
}

// DeviceSensors from the actual device
data class DeviceSensors(
    var humidity: Int,
    var temperature: Int
) {
    constructor() : this(0, 0)
}

data class DeviceDetail(
    var hasUpdate: Boolean,
    var dayStart: Long,
    var deviceName: String,
    var macAddress: String,
    var settings: DeviceSettings,
    var sensors: DeviceSensors,
    var isActive: Boolean
) {
    constructor() : this(
        false,
        0,
        "",
        "",
        DeviceSettings(Humidity(0, 0), Temperature(0, 0)),
        DeviceSensors(0, 0),
        false
    )
}