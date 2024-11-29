package pl.efento.mobile.bluetooth.example.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import pl.efento.mobile.bluetooth.example.platform.bluetooth.BluetoothAdapterManager
import pl.efento.mobile.bluetooth.example.platform.permissions.PermissionManager
import pl.efento.mobile.bluetooth.example.service.navigation.Destination
import pl.efento.mobile.bluetooth.example.service.navigation.NavigationService
import kotlin.coroutines.resume

internal class WelcomeViewModel(
    private val key: Destination.Welcome,
    private val navigationService: NavigationService,
    private val permissionManager: PermissionManager,
    private val bluetoothAdapterManager: BluetoothAdapterManager
) : ViewModel() {

    data class State(
        val isBluetoothStatusOk: Boolean,
        val isPermissionStatusOk: Boolean,
        val configureButtonText: String,
        val onConfigureButtonClick: () -> Unit,
    )

    private val initialState = State(
        isBluetoothStatusOk = false,
        isPermissionStatusOk = false,
        configureButtonText = "Configure permissions",
        onConfigureButtonClick = ::configure
    )
    private val _state = MutableStateFlow(initialState)
    private var configureJob: Job? = null

    val state: StateFlow<State> = _state.asStateFlow()

    init {
        _state.update { initialState }

        viewModelScope.launch {
            listenForPermissionsAndBluetoothStatus()
        }
    }

    private suspend fun listenForPermissionsAndBluetoothStatus() {
        configureJob?.cancelAndJoin()

        configureJob = viewModelScope.launch {
            withContext(Dispatchers.Default) {
                permissionStateFlow().zip(bluetoothStateFlow()) { p, b -> p to b }.collect { (isPermissionOk, isBluetoothOk) ->
                    if (isPermissionOk && isBluetoothOk) {
                        _state.update { state ->
                            state.copy(
                                isPermissionStatusOk = true,
                                isBluetoothStatusOk = true
                            )
                        }
                        navigationService.navigate(Destination.List)
                        this@launch.cancel()
                    }

                    _state.update { state ->
                        state.copy(
                            configureButtonText = if (!isPermissionOk) "Configure permissions" else "Enable Bluetooth",
                            isPermissionStatusOk = isPermissionOk,
                            isBluetoothStatusOk = isBluetoothOk
                        )
                    }
                }
            }
        }
    }

    // ---

    private fun configure() {
        if (!permissionManager.checkPermissions()) {
            permissionManager.requestPermissions()
            return
        }

        bluetoothAdapterManager.enableBluetooth()
    }

    private suspend fun permissionStateFlow(): Flow<Boolean> = suspendCancellableCoroutine { continuation ->
        val flow = MutableSharedFlow<Boolean>()

        val permissionStateJob = viewModelScope.launch {
            while (this@launch.isActive) {
                flow.emit(permissionManager.checkPermissions())
                delay(1_000)
            }
        }

        continuation.invokeOnCancellation {
            permissionStateJob.cancel()
        }

        continuation.resume(flow)
    }

    private suspend fun bluetoothStateFlow(): Flow<Boolean> = suspendCancellableCoroutine { continuation ->
        val flow = MutableSharedFlow<Boolean>()

        val permissionStateJob = viewModelScope.launch {
            while (this@launch.isActive) {
                flow.emit(bluetoothAdapterManager.isBluetoothEnabled())
                delay(1_000)
            }
        }

        continuation.invokeOnCancellation {
            permissionStateJob.cancel()
        }

        continuation.resume(flow)
    }
}