plugins {
    kotlin("jvm") version "1.9.0"
}

allprojects {
    group = "org.bclipse"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects{
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")

    dependencies {
        testImplementation(kotlin("test"))
    }

    kotlin {
        jvmToolchain(17)
    }

    tasks.test {
        useJUnitPlatform()
    }
}