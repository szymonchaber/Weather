package dev.szymonchaber.weather.data.cache

import dev.szymonchaber.weather.domain.model.Weather
import kotlinx.datetime.Instant

internal data class CachedWeather(val weather: Weather, val storedAt: Instant)
