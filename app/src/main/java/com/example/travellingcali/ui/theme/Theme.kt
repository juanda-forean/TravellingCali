package com.example.travellingcali.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = GreenAccent,
    onPrimary = BlackBg,
    background = BlackBg,
    onBackground = WhiteText,
    surface = DarkSurface,
    onSurface = WhiteText,
    secondary = GreenAccent
)

@Composable
fun TravellingCaliTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
