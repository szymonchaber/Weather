package dev.szymonchaber.weather.domain.usecase

import dev.szymonchaber.weather.domain.model.Coordinates
import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.repository.ForecastRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    suspend fun getForecast(coordinates: Coordinates): RequestResult<Forecast, ForecastError> {
        return repository.getForecast(coordinates)
    }
}
