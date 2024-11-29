package pl.efento.mobile.bluetooth.example.platform

import org.koin.dsl.bind
import org.koin.dsl.module
import pl.efento.mobile.bluetooth.example.platform.bluetooth.BluetoothAdapterManager
import pl.efento.mobile.bluetooth.example.platform.bluetooth.getBluetoothAdapterManager
import pl.efento.mobile.bluetooth.example.platform.permissions.PermissionManager
import pl.efento.mobile.bluetooth.example.platform.permissions.getPermissionManager

internal object PlatformModule {
    fun provide() = module {
        single { getPermissionManager() } bind PermissionManager::class
        single { getBluetoothAdapterManager() } bind BluetoothAdapterManager::class
    }
}