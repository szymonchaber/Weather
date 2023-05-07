package dev.szymonchaber.weather.domain.model

sealed interface WeatherError {

    object NetworkError : WeatherError

    data class UnknownError(val exception: Exception) : WeatherError
}
