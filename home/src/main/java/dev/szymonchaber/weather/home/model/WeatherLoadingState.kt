package dev.szymonchaber.weather.home.model

import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.Weather
import dev.szymonchaber.weather.domain.model.WeatherError

sealed interface WeatherLoadingState {

    object Loading : WeatherLoadingState

    data class Success(val weather: Weather, val location: Location) : WeatherLoadingState

    data class Error(val weatherError: WeatherError) : WeatherLoadingState
}
