package dev.szymonchaber.weather.home.model

import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.Weather
import dev.szymonchaber.weather.domain.model.WeatherError

data class HomeState(
    val weatherState: WeatherLoadingState = WeatherLoadingState.Loading,
    val teleportationProgress: Float = 0f,
    val location: Location? = null
) {

    fun withForecastSuccess(weather: Weather): HomeState {
        return copy(weatherState = WeatherLoadingState.Success(weather))
    }

    fun withForecastError(weatherError: WeatherError): HomeState {
        return copy(weatherState = WeatherLoadingState.Error(weatherError))
    }

    fun withForecastLoading() : HomeState {
        return copy(weatherState = WeatherLoadingState.Loading)
    }

    fun withLocation(location: Location) : HomeState {
        return copy(location = location)
    }

    companion object {

        val initial: HomeState = HomeState()
    }
}
