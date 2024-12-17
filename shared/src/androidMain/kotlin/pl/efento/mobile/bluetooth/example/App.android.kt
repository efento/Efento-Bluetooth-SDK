package pl.efento.mobile.bluetooth.example

import android.app.Application
import android.util.Log
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pl.efento.mobile.bluetooth.EfentoBluetooth
import pl.efento.mobile.bluetooth.log.BluetoothLogListener

internal actual fun initEfentoBluetooth() {
    val context = object : KoinComponent {
        val value: Application by inject()
    }.value

    EfentoBluetooth.init(context, AndroidLogger)
}

private object AndroidLogger : BluetoothLogListener {
    override fun log(level: BluetoothLogListener.Level, tag: String, message: String) {
        when (level) {
            BluetoothLogListener.Level.DEBUG -> Log.d(tag, message)
            BluetoothLogListener.Level.INFO  -> Log.i(tag, message)
            BluetoothLogListener.Level.WARN  -> Log.w(tag, message)
            BluetoothLogListener.Level.ERROR -> Log.e(tag, message)
        }
    }

    override fun log(level: BluetoothLogListener.Level, tag: String, message: String, throwable: Throwable) {
        when (level) {
            BluetoothLogListener.Level.DEBUG -> Log.d(tag, message)
            BluetoothLogListener.Level.INFO  -> Log.i(tag, message)
            BluetoothLogListener.Level.WARN  -> Log.w(tag, message, throwable)
            BluetoothLogListener.Level.ERROR -> Log.e(tag, message, throwable)
        }
    }

}