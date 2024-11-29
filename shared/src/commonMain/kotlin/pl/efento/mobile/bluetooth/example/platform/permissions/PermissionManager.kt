package pl.efento.mobile.bluetooth.example.platform.permissions

internal interface PermissionManager {
    fun requestPermissions()
    fun checkPermissions(): Boolean
}

internal expect fun getPermissionManager(): PermissionManager