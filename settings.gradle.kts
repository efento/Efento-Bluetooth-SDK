enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            name = "efentoMaven"
            url = uri("https://maven.efento.io/releases")
        }
    }
}

rootProject.name = "example"
include(":shared")
include(":androidApp")