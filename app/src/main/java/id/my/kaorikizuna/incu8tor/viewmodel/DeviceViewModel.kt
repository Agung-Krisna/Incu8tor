package id.my.kaorikizuna.incu8tor.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.my.kaorikizuna.incu8tor.model.Device
import id.my.kaorikizuna.incu8tor.model.DeviceDetail


class DeviceViewModel() : ViewModel() {

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
                    if (device != null) {
                        devices.add(device)
                        Log.d(TAG, "onDataChange: $device")
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

    fun addDevice(macAddress: String, device: DeviceDetail, onSuccess: () -> Unit) {
        devicesReference.child(macAddress).setValue(device)
    }

}