package pl.efento.mobile.bluetooth.example

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() {
    App.init()
    return ComposeUIViewController { App.Main() }
}