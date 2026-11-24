package com.android.stocks.core.designsystem.theme

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
    primary = Purple,
    onPrimary = Color.White,
    primaryContainer = PurpleContainer,
    onPrimaryContainer = OnPurpleContainer,
    secondary = Pink,
    onSecondary = Color.White,
    secondaryContainer = PinkContainer,
    background = NeutralLight,
    onBackground = Color(0xFF1C1B1E),
    surface = SurfaceLight,
    onSurface = Color(0xFF1C1B1E),
)

private val DarkColors = darkColorScheme(
    primary = PurpleLight,
    onPrimary = Color(0xFF35003A),
    primaryContainer = PurpleDark,
    onPrimaryContainer = PurpleContainer,
    secondary = Pink,
    onSecondary = Color.White,
    background = NeutralDark,
    onBackground = Color(0xFFE6E1E6),
    surface = SurfaceDark,
    onSurface = Color(0xFFE6E1E6),
)

@Composable
fun StocksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = StocksTypography,
        content = content,
    )
}
