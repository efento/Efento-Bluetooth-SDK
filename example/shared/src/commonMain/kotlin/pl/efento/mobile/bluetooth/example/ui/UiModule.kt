package pl.efento.mobile.bluetooth.example.ui

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import pl.efento.mobile.bluetooth.example.service.navigation.Destination
import pl.efento.mobile.bluetooth.example.ui.details.DetailsViewModel
import pl.efento.mobile.bluetooth.example.ui.list.ListViewModel
import pl.efento.mobile.bluetooth.example.ui.welcome.WelcomeViewModel

internal object UiModule {
    fun provide() = module {
        viewModel<WelcomeViewModel> { (key: Destination.Welcome) -> WelcomeViewModel(key, get(), get(), get()) }
        viewModel<DetailsViewModel> { (key: Destination.Device) -> DetailsViewModel(key, get()) }
        viewModel<ListViewModel> { (key: Destination.List) -> ListViewModel(key, get()) }
    }
}