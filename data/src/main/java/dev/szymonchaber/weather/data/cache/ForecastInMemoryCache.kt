package dev.szymonchaber.weather.data.cache

import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.Weather
import kotlinx.datetime.Clock
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class ForecastInMemoryCache @Inject constructor() {

    private val locationToCachedWeather = mutableMapOf<Location, CachedWeather>()

    fun get(location: Location): CacheResult? {
        return locationToCachedWeather[location]?.let {
            val staleCutoffDate = Clock.System.now().minus(10.toDuration(DurationUnit.MINUTES))
            val isStale = it.storedAt < staleCutoffDate
            Timber.d("Cache hit! Is stale = $isStale")
            CacheResult(result = it.weather, isStale = isStale)
        } ?: kotlin.run {
            Timber.d("Cache miss")
            null
        }
    }

    fun store(location: Location, weather: Weather) {
        locationToCachedWeather[location] = CachedWeather(weather, Clock.System.now())
    }
}
