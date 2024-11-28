package pl.efento.mobile.bluetooth.example.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.efento.mobile.bluetooth.example.service.navigation.Destination
import pl.efento.mobile.bluetooth.example.service.navigation.NavigationService

@Composable
fun ListRouter() {
    KoinContext {
        val navigationService = koinInject<NavigationService>()
        val viewModel = koinViewModel<ListViewModel>(
            viewModelStoreOwner = navigationService.key<Destination.List>(),
            parameters = { parametersOf(navigationService.key<Destination.List>()) }
        )
        val state by viewModel.state.collectAsState()

        ListScreen(
            error = state.error,
            devices = state.devices,
            onDeviceClick = state.onDeviceClick
        )

        LaunchedEffect(Unit) {
            viewModel.onAttached()
        }
    }
}