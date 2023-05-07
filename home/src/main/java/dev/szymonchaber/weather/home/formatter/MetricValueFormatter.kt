package dev.szymonchaber.weather.home.formatter

import dev.szymonchaber.weather.domain.model.Temperature

object MetricValueFormatter : ValueFormatter {

    override fun format(temperature: Temperature): String {
        return "${temperature.celsius}°C"
    }
}
