import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
}

group = "com.bclipse"
version = "1.0-SNAPSHOT"
val artifactName = "bclipse-plugin-core"
val mcApiVersion: String by project
val simpleMcApiVersion: String by project
val pluginDirectoryPath: String by project
val legacyPluginDirectoryPath: String by project

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.insert-koin:koin-core:3.1.2")
    compileOnly("org.spigotmc:spigot-api:${mcApiVersion}")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

val java17 = 17

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(java17))
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        if (java17 >= 10 || JavaVersion.current().isJava10Compatible()) {
            options.release.set(java17)
        }
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = java17.toString()
    }

    test {
        useJUnitPlatform()
    }
}
