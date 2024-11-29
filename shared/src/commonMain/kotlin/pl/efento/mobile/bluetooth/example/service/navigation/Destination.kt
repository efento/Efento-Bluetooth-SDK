package pl.efento.mobile.bluetooth.example.service.navigation

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import pl.efento.mobile.bluetooth.model.BluetoothMacAddress
import pl.efento.mobile.bluetooth.model.DeviceID

internal sealed class Destination: ViewModelStoreOwner {
    data object Welcome : Destination() {
        override val viewModelStore: ViewModelStore = ViewModelStore()
    }

    data object List : Destination() {
        override val viewModelStore: ViewModelStore = ViewModelStore()
    }

    data class Device(val deviceId: DeviceID, val address: BluetoothMacAddress) : Destination() {
        override val viewModelStore: ViewModelStore = ViewModelStore()
    }
}