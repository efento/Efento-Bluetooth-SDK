package pl.efento.mobile.bluetooth.example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    private val activityModule = module {
        single<Activity> { this@MainActivity }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        App.koinInstance.loadModules(listOf(activityModule))

        setContent {
            App.Main()
        }
    }
}