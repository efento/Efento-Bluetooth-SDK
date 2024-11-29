package pl.efento.mobile.bluetooth.example.platform.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.core.content.ContextCompat
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class BluetoothAdapterManagerImpl : BluetoothAdapterManager, KoinComponent {
    private val activity by inject<Activity>()
    private val adapter = BluetoothAdapter.getDefaultAdapter()

    override fun isBluetoothEnabled(): Boolean {
        return adapter.isEnabled
    }

    override fun enableBluetooth() {
        if (!adapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ContextCompat.startActivity(activity, enableBtIntent, null)
        }
    }
}

internal actual fun getBluetoothAdapterManager(): BluetoothAdapterManager = BluetoothAdapterManagerImpl()