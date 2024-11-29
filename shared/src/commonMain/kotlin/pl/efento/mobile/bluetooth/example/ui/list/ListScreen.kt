package pl.efento.mobile.bluetooth.example.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.efento.mobile.bluetooth.model.BluetoothMacAddress
import pl.efento.mobile.bluetooth.model.DeviceID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    error: String?,
    devices: List<Pair<DeviceID, BluetoothMacAddress>>,
    onDeviceClick: (DeviceID, BluetoothMacAddress) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Efento example") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = if (error != null) Alignment.Center else Alignment.TopCenter
        ) {
            if (error != null) {
                Text(text = error)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(devices.size) { index ->
                        val (id, address) = devices[index]
                        DeviceItem(
                            deviceID = id,
                            address = address,
                            color = if (index % 2 == 0) Color(33f, 33f, 33f, .33f) else Color.Unspecified,
                            onClick = onDeviceClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeviceItem(
    deviceID: DeviceID,
    address: BluetoothMacAddress,
    color: Color,
    onClick: (DeviceID, BluetoothMacAddress) -> Unit
) {
    Surface(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        color = color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clickable(onClick = { onClick(deviceID, address) }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = address.withColons)
        }
    }
}