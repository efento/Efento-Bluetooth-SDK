package pl.efento.mobile.bluetooth.example

import android.app.Application
import org.koin.dsl.module

class Application : Application() {
    private val appModule = module {
        single<Application> { this@Application }
    }

    override fun onCreate() {
        super.onCreate()

        App.koinInstance.loadModules(listOf(appModule))
        App.init()
    }
}