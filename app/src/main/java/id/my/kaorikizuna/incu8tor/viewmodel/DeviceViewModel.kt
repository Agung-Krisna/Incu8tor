package id.my.kaorikizuna.incu8tor.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.my.kaorikizuna.incu8tor.model.DeviceDetail
import id.my.kaorikizuna.incu8tor.model.toMap


class DeviceViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val devicesReference = database.getReference("devices")

    // get all devices
    fun getAllDevices(
        onSuccess: (List<DeviceDetail>) -> Unit = { emptyList<DeviceDetail>() },
        onFailure: (Exception) -> Unit = {}
    ) {
        devicesReference.addValueEventListener(object : ValueEventListener {
            // get realtime data change within the database
            override fun onDataChange(snapshot: DataSnapshot) {
                val devices = mutableListOf<DeviceDetail>()

                // get data from snapshot
                for (deviceSnapshot in snapshot.children) {
                    val device = deviceSnapshot.getValue(DeviceDetail::class.java)
                    if (device != null && device.active) {
                        device.macAddress = deviceSnapshot.key.toString()
                        devices.add(device)
                    }

                }
                return onSuccess(devices)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
                return onFailure(error.toException())
            }
        })
    }

    fun addDevice(device: DeviceDetail, onSuccess: () -> Unit = {}) {
        device.active = true
        devicesReference.child(device.macAddress).updateChildren(device.toMap())
    }

    fun updateDevice(device: DeviceDetail, onSuccess: () -> Unit = {}) {
        device.hasUpdate = true
        devicesReference.child(device.macAddress).updateChildren(device.toMap())
    }

    fun getDevice(macAddress: String, onSuccess: (DeviceDetail) -> Unit) {
        devicesReference.child(macAddress).get().addOnSuccessListener {
            // getValue method deserializes the data into a DeviceDetail object
            val device = it.getValue(DeviceDetail::class.java)!!
            device.macAddress = it.key.toString()
            Log.d(TAG, "getDevice: $device")
            onSuccess(device)
        }
    }

    fun deleteDevice(device: DeviceDetail) {
        // performing soft deletion of the device
        val deactivatedDevice = mapOf("active" to false)
        devicesReference.child(device.macAddress).updateChildren(deactivatedDevice)
    }
}