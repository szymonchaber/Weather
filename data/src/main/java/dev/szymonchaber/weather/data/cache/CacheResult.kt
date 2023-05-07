package dev.szymonchaber.weather.data.cache

import dev.szymonchaber.weather.domain.model.Weather

internal data class CacheResult(val result: Weather, val isStale: Boolean)
