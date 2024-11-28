package pl.efento.mobile.bluetooth.example.ui.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.efento.mobile.bluetooth.example.service.navigation.Destination
import pl.efento.mobile.bluetooth.example.service.navigation.NavigationService

@Composable
fun WelcomeRouter() {
    KoinContext {
        val navigationService = koinInject<NavigationService>()
        val viewModel = koinViewModel<WelcomeViewModel>(
            viewModelStoreOwner = navigationService.key<Destination.Welcome>(),
            parameters = { parametersOf(navigationService.key<Destination.Welcome>()) }
        )
        val state by viewModel.state.collectAsState()

        WelcomeScreen(
            isBluetoothStatusOk = state.isBluetoothStatusOk,
            isPermissionStatusOk = state.isPermissionStatusOk,
            configureButtonText = state.configureButtonText,
            onConfigureBluetoothClick = state.onConfigureButtonClick,
        )
    }
}
