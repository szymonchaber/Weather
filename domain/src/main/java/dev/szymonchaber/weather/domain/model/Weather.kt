package dev.szymonchaber.weather.domain.model

data class Weather(val currentWeather: CurrentWeather, val hourlyForecasts: List<HourlyForecast>)
