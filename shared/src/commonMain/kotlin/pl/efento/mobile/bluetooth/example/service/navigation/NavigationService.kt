package pl.efento.mobile.bluetooth.example.service.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.efento.mobile.bluetooth.example.util.logging.Logger

internal class NavigationService {
    private val logger = Logger("NavigationService")
    private val history = mutableListOf<Destination>(Destination.Welcome)

    private val _state = MutableStateFlow(history.last())
    val state = _state.asStateFlow()

    fun navigate(destination: Destination) {
        logger.debug { "Navigate to $destination" }

        history.add(destination)
        _state.update {
            destination
        }
    }

    inline fun <reified T : Destination> key(): T = history.filterIsInstance<T>().first()

    fun back() {
        logger.debug { "Navigate back" }

        if (history.size > 1) {
            val last = history.removeAt(history.size - 1)
            last.viewModelStore.clear()

            _state.update {
                history.last()
            }
        }
    }
}