pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "bclipse"
include("bclipse-plugin-core")
include("bclipse-plugin-economy")
include("bclipse-monolith")
include("bclipse-plugin-loader")
include("bclipse-lib")
