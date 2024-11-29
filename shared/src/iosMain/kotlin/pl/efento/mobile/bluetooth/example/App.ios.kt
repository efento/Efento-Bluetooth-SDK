package pl.efento.mobile.bluetooth.example

import pl.efento.mobile.bluetooth.ApplicationContext
import pl.efento.mobile.bluetooth.EfentoBluetooth
import pl.efento.mobile.bluetooth.log.NSLogger

actual fun initEfentoBluetooth() {
    EfentoBluetooth.init(ApplicationContext(), NSLogger())
}