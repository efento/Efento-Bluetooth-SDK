package pl.efento.mobile.bluetooth.example.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.efento.mobile.bluetooth.EfentoBluetooth
import pl.efento.mobile.bluetooth.example.service.navigation.Destination
import pl.efento.mobile.bluetooth.example.service.navigation.NavigationService
import pl.efento.mobile.bluetooth.example.util.time.timestampToString

internal class DetailsViewModel(
    private val key: Destination.Device,
    private val navigationService: NavigationService,
) : ViewModel() {

    data class State(
        val error: String?,
        val onBack: () -> Unit,
        val isLoading: Boolean,
        val measurements: List<Pair<String, String>>
    )

    private val initialState = State(
        onBack = ::back,
        error = null,
        isLoading = false,
        measurements = listOf())

    private val _state = MutableStateFlow<State>(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { initialState }
            downloadMeasurements()
        }
    }

    private fun back() {
        navigationService.back()
    }

    private fun downloadMeasurements(): Job = viewModelScope.launch {
        _state.update { state ->
            state.copy(
                error = null,
                isLoading = true,
            )
        }

        val connection = EfentoBluetooth.sensorConnection(
            deviceID = key.deviceId,
            bluetoothMacAddress = key.address,
            resetCode = 1111, // put your pin code here
            encryptionKey = "" // put your encryption key here
        )

        try {
            connection.connect()

            val measurements = connection.commands.downloadMeasurements { }.entries.first().value.flatMap { firstMeasurementPacket ->
                firstMeasurementPacket.measurements.map { measurement ->
                    timestampToString(measurement.timestamp) to measurement.measurement.value.toString()
                }
            }

            _state.update { state ->
                state.copy(
                    isLoading = false,
                    measurements = measurements,
                )
            }

        } catch (ex: Exception) {
            _state.update { state ->
                state.copy(
                    error = ex.toString(),
                )
            }
        } finally {
            connection.disconnect()
        }
    }
}