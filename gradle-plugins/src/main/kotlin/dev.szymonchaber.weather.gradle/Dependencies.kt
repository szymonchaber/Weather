package dev.szymonchaber.weather.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class Dependencies : Plugin<Project> {

    override fun apply(target: Project) = Unit

    companion object {

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"

        private const val hiltVersion = "2.44.2"
        private const val hiltLibrary = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltKapt = "com.google.dagger:hilt-compiler:$hiltVersion"

        private const val timber = "com.jakewharton.timber:timber:5.0.1"
        private const val kotlinxDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"

        val common = listOf(coroutines, hiltLibrary, timber, kotlinxDatetime)

        const val androidXCore = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:1.7.0-alpha02"

        private const val lifecycleVersion = "2.4.1"
        val lifecycle = listOf(
            "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion",
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion",
            "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
        )

        const val composeVersion = "1.4.0"
        val compose = listOf(
            "androidx.compose.ui:ui:$composeVersion",
            "androidx.compose.ui:ui-tooling-preview:$composeVersion",
            "androidx.compose.material:material:$composeVersion",
            "androidx.activity:activity-compose:1.7.0-beta01",
            "androidx.navigation:navigation-compose:2.5.3",
            "androidx.hilt:hilt-navigation-compose:1.0.0"
        )

        val debugUiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"

        private val composeDestinationsVersion = "1.8.33-beta"
        val composeDestinations = listOf(
            "io.github.raamcosta.compose-destinations:core:$composeDestinationsVersion",
            "io.github.raamcosta.compose-destinations:animations-core:$composeDestinationsVersion"
        )
        val composeDestinationsKsp =
            "io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion"

        private const val roomVersion = "2.4.2"

        val room = listOf(
            "androidx.room:room-runtime:$roomVersion",
            "androidx.room:room-ktx:$roomVersion",
            "androidx.room:room-paging:2.5.0-alpha01"
        )
        val roomKsp = "androidx.room:room-compiler:$roomVersion"

        val dataStore = "androidx.datastore:datastore-preferences:1.0.0"

        val ui = listOf(androidXCore, appCompat) + compose + lifecycle

        val arrow = "io.arrow-kt:arrow-core:1.0.1"

        val ktor = listOf(
            "io.ktor:ktor-client-android:2.3.0",
            "io.ktor:ktor-client-serialization:2.3.0",
            "io.ktor:ktor-client-content-negotiation:2.3.0",
            "io.ktor:ktor-serialization-kotlinx-json:2.3.0",
            "io.ktor:ktor-client-logging-jvm:2.3.0",
            "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"
        )

        val unitTest = listOf(
            "junit:junit:4.13.2",
            "com.google.truth:truth:1.1.3",
            "androidx.room:room-testing:$roomVersion",
            "org.mockito:mockito-core:5.3.1",
            "org.mockito.kotlin:mockito-kotlin:4.1.0"
        )

        val uiTest = listOf(
            "androidx.test.ext:junit:1.1.5",
            "androidx.test.espresso:espresso-core:3.5.1",
            "androidx.compose.ui:ui-test-junit4:$composeVersion",
            "androidx.room:room-testing:$roomVersion"
        )
    }
}
