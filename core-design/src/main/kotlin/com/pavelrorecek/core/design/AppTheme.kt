package com.pavelrorecek.core.design

import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColors(
    primary = Color.White,
    secondary = Color.White.copy(alpha = 0.5f),
    background = Color.Black,
)

@Composable
public fun AppTheme(
    content: @Composable () -> Unit,
) {
    val colors = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colors = colors,
        content = content,
    )
}
