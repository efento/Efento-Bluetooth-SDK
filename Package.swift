// swift-tools-version:5.3

import PackageDescription

let package = Package(
    name: "EfentoBluetoothSDK",
    platforms: [
        .iOS(.v15)
    ],
    products: [
        .library(name: "EfentoBluetoothSDK", targets: ["EfentoBluetoothSDK"])
    ],
    targets: [
        .binaryTarget(
            name: "EfentoBluetoothSDK",
            url: "https://github.com/efento/Efento-Bluetooth-SDK/releases/download/2.4.0/EfentoBluetoothSDK.xcframework.zip",
            checksum: "445d457bcc1b3c0055ea387af1c4f6278bf29ae3bc76d766aee107776a283834"
        )
    ]
)