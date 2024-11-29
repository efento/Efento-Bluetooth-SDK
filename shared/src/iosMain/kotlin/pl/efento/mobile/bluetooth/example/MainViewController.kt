package pl.efento.mobile.bluetooth.example

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    App.init()
    return ComposeUIViewController { App.Main() }
}