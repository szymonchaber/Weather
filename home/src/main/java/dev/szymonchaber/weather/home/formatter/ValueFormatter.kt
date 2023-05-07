package dev.szymonchaber.weather.home.formatter

import dev.szymonchaber.weather.domain.model.Temperature

interface ValueFormatter {

    fun format(temperature: Temperature): String
}
