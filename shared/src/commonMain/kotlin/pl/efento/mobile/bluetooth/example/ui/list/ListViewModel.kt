package pl.efento.mobile.bluetooth.example.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.efento.mobile.bluetooth.EfentoBluetooth
import pl.efento.mobile.bluetooth.example.service.navigation.Destination
import pl.efento.mobile.bluetooth.example.service.navigation.NavigationService
import pl.efento.mobile.bluetooth.model.BluetoothMacAddress
import pl.efento.mobile.bluetooth.model.DeviceID
import pl.efento.mobile.bluetooth.model.Sensor

internal class ListViewModel(
    private val key: Destination.List,
    private val navigationService: NavigationService,
) : ViewModel() {

    data class State(
        val error: String?,
        val devices: List<Pair<String, BluetoothMacAddress>>,
        val onDeviceClick: (DeviceID, BluetoothMacAddress) -> Unit,
    )

    private val initialState = State(
        devices = listOf(),
        onDeviceClick = ::onDeviceClick,
        error = null
    )
    private val _state = MutableStateFlow(initialState)
    internal val state: StateFlow<State> = _state.asStateFlow()

    private var sensorFlowJob: Job? = null

    init {
        _state.update { initialState }
        sensorFlowJob = scanForDevices()
    }

    internal fun onAttached() {
        if (sensorFlowJob == null) {
            sensorFlowJob = scanForDevices()
        }
    }

    private fun scanForDevices() = viewModelScope.launch {
        EfentoBluetooth.scanner().deviceFlow()
            .catch { onScanError(it) }
            .filterIsInstance(Sensor::class) // filter only Sensor objects
            .collect { device ->
                if (_state.value.devices.any { it.first.contentEquals(device.id) }) {
                    return@collect
                }

                _state.update { state ->
                    state.copy(
                        devices = state.devices + listOf(device.id to device.bluetoothMacAddress)
                    )
                }
            }
    }

    private fun onDeviceClick(deviceId: DeviceID, address: BluetoothMacAddress) {
        sensorFlowJob?.cancel()
        sensorFlowJob = null
        navigationService.navigate(Destination.Device(deviceId, address))
    }

    //---

    private fun onScanError(throwable: Throwable) {
        _state.update { state ->
            state.copy(
                error = "Error: ${throwable.message}"
            )
        }
    }
}