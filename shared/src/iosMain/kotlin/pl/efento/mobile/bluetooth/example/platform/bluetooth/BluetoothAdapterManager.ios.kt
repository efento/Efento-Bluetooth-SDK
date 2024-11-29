package pl.efento.mobile.bluetooth.example.platform.bluetooth

import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBCentralManagerOptionShowPowerAlertKey
import platform.CoreBluetooth.CBManagerState
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.darwin.NSObject

internal class BluetoothAdapterManagerImpl : BluetoothAdapterManager {
    private val delegate = BluetoothAdapterManagerDelegate // need to assign to a variable for init to be called

    override fun isBluetoothEnabled(): Boolean {
        return BluetoothAdapterManagerDelegate.isBluetoothOn()
    }

    override fun enableBluetooth() {
        BluetoothAdapterManagerDelegate.openAppSettings()
    }
}

internal actual fun getBluetoothAdapterManager(): BluetoothAdapterManager = BluetoothAdapterManagerImpl()

internal object BluetoothAdapterManagerDelegate {
    private var isEnabled = false
    private val delegate = object : CBCentralManagerDelegateProtocol, NSObject() {
        override fun centralManagerDidUpdateState(central: CBCentralManager) {
            isEnabled = central.state().isOn()
        }
    }
    private val centralManager = CBCentralManager(delegate, null, mapOf(CBCentralManagerOptionShowPowerAlertKey to true))

    init {
        isEnabled = centralManager.state().isOn()
    }

    fun isBluetoothOn(): Boolean {
        return isEnabled
    }

    fun openAppSettings() {
        openNSUrl(UIApplicationOpenSettingsURLString)
    }

    private fun openNSUrl(string: String) {
        val settingsUrl: NSURL = NSURL.URLWithString(string)!!
        if (UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
            UIApplication.sharedApplication.openURL(settingsUrl)
        }
    }
}

private fun CBManagerState.isOn(): Boolean = when (this) {
    CBManagerStatePoweredOn -> true
    else                    -> false
}