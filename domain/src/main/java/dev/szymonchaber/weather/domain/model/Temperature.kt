package dev.szymonchaber.weather.domain.model

data class Temperature(val celsius: Double) {

    val fahrenheit: Double
        get() = (celsius * 9.0 / 5.0) + 32.0
}
