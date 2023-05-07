package dev.szymonchaber.weather.data.repository

import dev.szymonchaber.weather.data.api.ForecastApi
import dev.szymonchaber.weather.data.cache.ForecastInMemoryCache
import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.RequestResult
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ForecastRepositoryImplTest {

    private val forecastApi = mock<ForecastApi>() {
        onBlocking { getForecast(any()) } doReturn RequestResult.success(mock())
    }

    private val cache = mock<ForecastInMemoryCache>()

    private val repository = ForecastRepositoryImpl(forecastApi, cache)

    @Test
    fun `given cache miss, when getForecast called, then should fetch from api`() {
        runBlocking {
            // given
            val location = mock<Location>()
            whenever(cache.get(location)) doReturn null

            // when
            repository.getForecast(location)

            // then
            verify(forecastApi).getForecast(location)
        }
    }

    @Test
    fun `given cache missed and api called, when getForecast called, then should store api result in cache`() {
        runBlocking {
            // given
            val location = mock<Location>()
            whenever(cache.get(location)) doReturn null

            // when
            repository.getForecast(location)

            // then
            verify(cache).store(eq(location), any())
        }
    }
}
