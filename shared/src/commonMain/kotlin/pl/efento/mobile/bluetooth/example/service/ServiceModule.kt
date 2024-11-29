package pl.efento.mobile.bluetooth.example.service

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.efento.mobile.bluetooth.example.service.navigation.NavigationService

internal object ServiceModule {
    fun provide() = module {
        singleOf(::NavigationService)
    }
}