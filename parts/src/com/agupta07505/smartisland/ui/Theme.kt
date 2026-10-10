/*
 * Smart Island (2026)
 * © Animesh Gupta — github.com/agupta07505
 * Licensed under the GNU GPL v3 License
 * Do not remove or alter this notice. - Per GPL-3.0 Section 4 & Section 5
 */

package com.agupta07505.smartisland.ui

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

// Shrinky "Nothing" palette: pure black / #1C1C1C cards in dark, #F2F2F2 / white in light, red #D71921 accent.
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFD71921),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFBE3E4),
    onPrimaryContainer = Color(0xFF5C0A0E),
    secondary = Color(0xFF606060),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEBEBEB),
    onSecondaryContainer = Color(0xFF000000),
    tertiary = Color(0xFFD71921),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFBE3E4),
    onTertiaryContainer = Color(0xFF5C0A0E),
    background = Color(0xFFF2F2F2),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    surfaceVariant = Color(0xFFEBEBEB),
    onSurfaceVariant = Color(0xFF606060),
    outline = Color(0xFFD9D9D9),
    outlineVariant = Color(0xFFE2E2E2)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD71921),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3A0A0D),
    onPrimaryContainer = Color(0xFFFFDAD9),
    secondary = Color(0xFFADADAD),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF292929),
    onSecondaryContainer = Color(0xFFFFFFFF),
    tertiary = Color(0xFFD71921),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF3A0A0D),
    onTertiaryContainer = Color(0xFFFFDAD9),
    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF1C1C1C),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF292929),
    onSurfaceVariant = Color(0xFFADADAD),
    outline = Color(0xFF323232),
    outlineVariant = Color(0xFF323232)
)

@Composable
fun SmartIslandTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Use our tailored premium palette by default for maximum brand consistency
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

