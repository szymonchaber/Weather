package dev.szymonchaber.weather.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColors(
    primary = Purple40,
    secondary = PurpleGrey40,
//    tertiary = Pink40
)

@Composable
fun WeatherTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorScheme,
        typography = Typography,
        content = content
    )
}
