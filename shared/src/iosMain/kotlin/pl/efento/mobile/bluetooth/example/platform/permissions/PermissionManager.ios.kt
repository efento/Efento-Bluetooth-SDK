package pl.efento.mobile.bluetooth.example.platform.permissions

import kotlinx.cinterop.ObjCSignatureOverride
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBConnectionEvent
import platform.CoreBluetooth.CBManagerAuthorization
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerAuthorizationRestricted
import platform.CoreBluetooth.CBPeripheral
import platform.Foundation.NSError
import platform.Foundation.NSNumber
import platform.darwin.NSObject

internal class PermissionManagerImpl : PermissionManager {
    private val callbackManager = CentralBluetoothCallbackManager()

    override fun requestPermissions() {
        callbackManager.requestAuthorization {  }
    }

    override fun checkPermissions(): Boolean {
        return callbackManager.getAuthorizationStatus()
    }
}

internal actual fun getPermissionManager(): PermissionManager = PermissionManagerImpl()

private class CentralBluetoothCallbackManager : NSObject(), CBCentralManagerDelegateProtocol {
    private val bluetoothManager = CBCentralManager(this, null, null)
    private var callback: ((Boolean) -> Unit)? = null

    fun getAuthorizationStatus() = bluetoothManager.authorization.toPermissionState()

    fun requestAuthorization(callback: (Boolean) -> Unit) {
        this.callback = callback
        bluetoothManager.authorization()
    }

    override fun centralManagerDidUpdateState(central: CBCentralManager) {
        val state = central.authorization.toPermissionState()
        callback?.invoke(state)
        callback = null
    }

    @ObjCSignatureOverride
    override fun centralManager(central: CBCentralManager, didConnectPeripheral: CBPeripheral) {
    }

    @ObjCSignatureOverride
    override fun centralManager(central: CBCentralManager, didFailToConnectPeripheral: CBPeripheral, error: NSError?) {
    }

    @ObjCSignatureOverride
    override fun centralManager(central: CBCentralManager, didDisconnectPeripheral: CBPeripheral, error: NSError?) {
    }

    override fun centralManager(central: CBCentralManager, connectionEventDidOccur: CBConnectionEvent, forPeripheral: CBPeripheral) {}

    override fun centralManager(central: CBCentralManager, didDiscoverPeripheral: CBPeripheral, advertisementData: Map<Any?, *>, RSSI: NSNumber) {}

    @ObjCSignatureOverride
    override fun centralManager(central: CBCentralManager, didUpdateANCSAuthorizationForPeripheral: CBPeripheral) {
    }

    override fun centralManager(central: CBCentralManager, willRestoreState: Map<Any?, *>) {}
}

private fun CBManagerAuthorization.toPermissionState(): Boolean = when (this) {
    CBManagerAuthorizationAllowedAlways,
    CBManagerAuthorizationRestricted -> true
    else                                                    -> false
}