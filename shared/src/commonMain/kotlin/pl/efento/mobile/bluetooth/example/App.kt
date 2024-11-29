package pl.efento.mobile.bluetooth.example

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import pl.efento.mobile.bluetooth.example.platform.PlatformModule
import pl.efento.mobile.bluetooth.example.service.ServiceModule
import pl.efento.mobile.bluetooth.example.service.navigation.Destination
import pl.efento.mobile.bluetooth.example.service.navigation.NavigationService
import pl.efento.mobile.bluetooth.example.ui.UiModule
import pl.efento.mobile.bluetooth.example.ui.details.DetailsRouter
import pl.efento.mobile.bluetooth.example.ui.list.ListRouter
import pl.efento.mobile.bluetooth.example.ui.welcome.WelcomeRouter

object App : KoinComponent {
    private val navigationService by inject<NavigationService>()
    val koinInstance = startKoin {
        modules(
            UiModule.provide(),
            ServiceModule.provide(),
            PlatformModule.provide(),
        )
    }.koin

    fun init() {
        initEfentoBluetooth()
    }

    @Composable
    fun Main() {
        val destination by navigationService.state.collectAsState()

        val lightColors = lightColorScheme(
            primary = Color(0xff430e5e),
            onPrimary = Color(0xffffffff),
        )

        val darkColors = darkColorScheme(
            primary = Color(0xffda0d82),
            onPrimary = Color(0xffffffff),
        )

        val colors = if (isSystemInDarkTheme()) darkColors else lightColors

        MaterialTheme(
            colorScheme = colors,
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 8.dp)
                ) {
                    when (destination) {
                        is Destination.Welcome -> WelcomeRouter()
                        is Destination.List    -> ListRouter()
                        is Destination.Device  -> DetailsRouter()
                    }
                }
            }
        }
    }
}

internal expect fun initEfentoBluetooth()