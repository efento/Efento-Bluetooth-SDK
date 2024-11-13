# Efento Bluetooth Library
![iOS Badge](https://img.shields.io/badge/platform-Android-green) ![iOS Badge](https://img.shields.io/badge/platform-iOS-blue) [![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-indigo)](http://kotlinlang.org) [![kotlinx-coroutines](https://img.shields.io/badge/kotlinx--coroutines-1.9.0-indigo)](http://kotlinlang.org) 

Efento Bluetooth library allows scanning for and connecting to Efento Bluetooth Low Energy (BLE) devices.

This Kotlin Multiplatform library is built to support both Android and iOS, and uses [kotlin coroutines](https://kotlinlang.org/docs/coroutines-guide.html).

It's intended for use in multiplatform projects targeting Kotlin/Android and Kotlin/Native (iOS).

## Setup

Add the Efento Maven repository to your project

```groovy
dependencyResolutionManagement {
    repositories {
        maven("https://maven.efento.io/releases")
    }
}
```

Include the Library Dependency

```groovy
commonMain.dependencies {
    implementation("pl.efento.mobile:bluetooth:1.0.2")
}
```

## Quick start

Initialize the library by passing in the application context.

Android
```kotlin
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        EfentoBluetooth.init(this)
    }
}
```

iOS
```kotlin
EfentoBluetooth.init(IosNoOpApplicationContext()) //application context is not applicable for iOS
```

## Scanning for devices

Set scanner parameters and start collecting device data using the [deviceFlow](https://kotlinlang.org/docs/flow.html#flows) method.

```kotlin
val scanner = EfentoBluetooth.scanner(
    //scan parameters
)

scanner.deviceFlow()
       .catch { e -> log("Exception during scan", e) }
       .collect { efentoDevice -> 
           //handle scanned device here
       }
```

## Connecting to a Device

Define connection parameters and execute commands as needed.

```kotlin
val connection = EfentoBluetooth.sensorConnection(
        deviceID = "28:2C:02:40:2F:9C",
        bluetoothMacAddress = BluetoothMacAddress("28:2C:02:40:2F:9C"),
        resetCode = 5923
        //other connection parameters
    )

connection.run {
    connect()

    val measurements = commands.downloadMeasurements() { progress ->
        log.debug { "Progress: $progress" }
    }

    disconnect()
}
```

## Documentation

Access the full KDoc documentation for detailed information on usage and functions [here](https://efento.github.io/Efento-Bluetooth-SDK/)
