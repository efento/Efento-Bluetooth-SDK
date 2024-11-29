package pl.efento.mobile.bluetooth.example.platform.permissions

import android.app.Activity
import android.os.Build
import androidx.core.app.ActivityCompat
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class PermissionManagerImpl : PermissionManager, KoinComponent {
    private val activity by inject<Activity>()
    private val bluetoothPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.BLUETOOTH_CONNECT,
        )
    } else {
        listOf(
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun requestPermissions() {
        ActivityCompat.requestPermissions(activity, bluetoothPermissions.toTypedArray(), 1)
    }

    override fun checkPermissions(): Boolean {
        var arePermissionsGranted = true

        bluetoothPermissions.forEach {
            arePermissionsGranted = arePermissionsGranted && ActivityCompat.checkSelfPermission(activity, it) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }

        return arePermissionsGranted
    }
}

internal actual fun getPermissionManager(): PermissionManager = PermissionManagerImpl()