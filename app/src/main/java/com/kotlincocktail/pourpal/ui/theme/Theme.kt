package com.kotlincocktail.pourpal.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = Beige,
    secondary = Gray,
    tertiary = LightGray,
    background = DarkBlue,
    surface = DarkBlue,
    onPrimary = Black,
    onSecondary = LightGray,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Beige,
)

private val lightColorScheme = lightColorScheme(
    primary = Beige,
    secondary = Gray,
    tertiary = LightGray,
    background = DarkBlue,
    surface = DarkBlue,
    onPrimary = Black,
    onSecondary = LightGray,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Beige,
)

@Composable
fun PourPalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkBlue.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = Typography,
        content = content
    )

    
}