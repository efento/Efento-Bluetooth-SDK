package pl.efento.mobile.bluetooth.example.platform.bluetooth

internal interface BluetoothAdapterManager {
    fun isBluetoothEnabled(): Boolean
    fun enableBluetooth()
}

internal expect fun getBluetoothAdapterManager(): BluetoothAdapterManager