# Efento Bluetooth Library
![iOS Badge](https://img.shields.io/badge/platform-Android-green) ![iOS Badge](https://img.shields.io/badge/platform-iOS-blue) [![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-indigo)](http://kotlinlang.org) 

Efento Bluetooth library allows scanning for and connecting to bluetooth low energy devices manufactured by Efento.

It is a multiplatform library written in kotlin, targetting Android and iOS. It makes use of [kotlin coroutines](https://kotlinlang.org/docs/coroutines-guide.html) and is intented to be used in kotlin multiplatform projects.

## Setup

Add Efento maven to your project

```groovy
dependencyResolutionManagement {
    repositories {
        maven("https://maven.efento.io/releases")
    }
}
```

Include the library in the project as a dependency of your common target.

```groovy
commonMain.dependencies {
    implementation("pl.efento.mobile:bluetooth:1.0.2")
}
```

## Quick start

Initialize the library and pass in an application context.

Android
```kotlin
class Application : Application(), KoinComponent {
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

Define scanner parameters and run collect on the [deviceFlow](https://kotlinlang.org/docs/flow.html#flows) method

```kotlin
val scanner = EfentoBluetooth.scanner(
    //scan parameters
)

scope.launch {
    scanner.deviceFlow()
           .catch { e -> log("Exception during scan", e) }
           .collect { efentoDevice -> 
               //handle scanned device here
           }
}

```

## Connecting to device

Define connection parameters and run appriopriate command

```kotlin
EfentoBluetooth.sensorConnection(
        deviceID = "28:2C:02:40:2F:9C",
        bluetoothMacAddress = BluetoothMacAddress("28:2C:02:40:2F:9C"),
        resetCode = 5923
        //other connection parameters
    ).run {
        connect()

        val measurements = commands.downloadMeasurements() { progress ->
            log.debug { "Progress: $progress" }
        }
        
        disconnect()
    }
```

## Documentation

Detailed KDoc documentation is available [here](https://efento.github.io/Efento-Bluetooth-SDK/)
