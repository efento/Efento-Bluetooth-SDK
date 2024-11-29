package pl.efento.mobile.bluetooth.example.ui.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WelcomeScreen(
    isBluetoothStatusOk: Boolean,
    isPermissionStatusOk: Boolean,
    configureButtonText: String,
    onConfigureBluetoothClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Efento example") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Button(
                onClick = onConfigureBluetoothClick,
            ) {
                Text(configureButtonText)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                StatusText(
                    text = "Bluetooth adapter",
                    isOk = isBluetoothStatusOk,
                )

                StatusText(
                    text = "Bluetooth permissions",
                    isOk = isPermissionStatusOk,
                )
            }
        }
    }
}

@Composable
private fun StatusText(
    text: String,
    isOk: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(.7f),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = text)

        if (isOk) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
            )
        } else {
            CircularProgressIndicator(
                strokeWidth = 3.dp,
                modifier = Modifier
                    .size(24.dp)
                    .padding(2.dp),
            )
        }
    }
}
