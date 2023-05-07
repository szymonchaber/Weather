plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

gradlePlugin {
    plugins {
        create("dependencies") {
            id = "dependencies"
            implementationClass = "dev.szymonchaber.weather.gradle.Dependencies"
        }
    }
}
