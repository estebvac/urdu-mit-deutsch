package com.estebvac.urdudeutsch

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = Color(0xFF176B4D),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB8F1D4),
    onPrimaryContainer = Color(0xFF002116),
    secondary = Color(0xFF50645A),
    secondaryContainer = Color(0xFFD3E8DC),
    tertiary = Color(0xFF6A5D2F),
    tertiaryContainer = Color(0xFFF3E2A6),
    background = Color(0xFFFFFBF5),
    surface = Color(0xFFFFFBF5),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF9CD5B9),
    onPrimary = Color(0xFF003826),
    primaryContainer = Color(0xFF005138),
    onPrimaryContainer = Color(0xFFB8F1D4),
    secondary = Color(0xFFB7CCC0),
    tertiary = Color(0xFFD6C68C),
)

@Composable
fun UrduMitDeutschTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colors = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme -> dynamicDarkColorScheme(context)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> dynamicLightColorScheme(context)
        darkTheme -> DarkColors
        else -> LightColors
    }
    MaterialTheme(colorScheme = colors, content = content)
}
