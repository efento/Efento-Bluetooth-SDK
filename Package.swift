// swift-tools-version: 5.3

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
            url: "https://github.com/efento/Efento-Bluetooth-SDK/releases/download/2.3.0/EfentoBluetoothSDK.xcframework.zip",
            checksum: "f8d238e0a77014829e6a94caa86baaae294873744dbf2881b797d135c96f20e0"
        )
    ]
)