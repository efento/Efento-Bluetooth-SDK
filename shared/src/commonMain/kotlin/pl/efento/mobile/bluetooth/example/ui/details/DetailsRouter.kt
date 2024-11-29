package pl.efento.mobile.bluetooth.example.ui.details

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
internal fun DetailsRouter() {
    KoinContext {
        val navigationService = koinInject<NavigationService>()
        val viewModel = koinViewModel<DetailsViewModel>(
            viewModelStoreOwner = navigationService.key<Destination.Device>(),
            parameters = { parametersOf(navigationService.key<Destination.Device>()) }
        )

        val state by viewModel.state.collectAsState()

        DetailsScreen(
            onBack = state.onBack,
            error = state.error,
            isLoading = state.isLoading,
            measurements = state.measurements
        )
    }
}