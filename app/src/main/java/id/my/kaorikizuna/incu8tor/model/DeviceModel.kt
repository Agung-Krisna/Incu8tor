package id.my.kaorikizuna.incu8tor.model

import com.google.firebase.database.PropertyName

import androidx.lifecycle.ViewModel

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
    @PropertyName("-hasUpdate")
    var hasUpdate: Boolean,
    var dayStart: Long,
    var deviceName: String,
    var active: Boolean,
    var macAddress: String,
    var settings: DeviceSettings,
    var sensors: DeviceSensors,
) {
    // computed property
    val insensitiveName: String
        get() = deviceName.lowercase()

    constructor() : this(
        false,
        0,
        "",
        false,
        "",
        DeviceSettings(Humidity(0, 0), Temperature(0, 0)),
        DeviceSensors(0, 0),
    )
}

// making a map of the DeviceDetail data class to be serializable for transfer
// using .updateChildren() method
fun DeviceDetail.toMap(): Map<String, Any> {
    return mapOf(
        "hasUpdate" to hasUpdate,
        "dayStart" to dayStart,
        "deviceName" to deviceName,
        "active" to active,
        "macAddress" to macAddress,
        "settings" to settings.toMap(),
        "sensors" to sensors.toMap()
    )
}

fun DeviceSettings.toMap(): Map<String, Any> {
    return mapOf(
        "humidity" to humidity.toMap(),
        "temperature" to temperature.toMap()
    )
}

fun DeviceSensors.toMap(): Map<String, Any> {
    return mapOf(
        "humidity" to humidity,
        "temperature" to temperature
    )
}

fun Humidity.toMap(): Map<String, Any> {
    return mapOf(
        "min" to min,
        "max" to max
    )
}

fun Temperature.toMap(): Map<String, Any> {
    return mapOf(
        "min" to min,
        "max" to max
    )
}
