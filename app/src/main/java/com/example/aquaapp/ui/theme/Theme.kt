package com.example.aquaapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Тема всегда тёмная и не зависит от настроек системы
private val AquaDarkColorScheme = darkColorScheme(
    primary = Teal,
    onPrimary = NavyBg,
    secondary = TealLight,
    onSecondary = NavyBg,
    tertiary = TealDark,
    background = NavyBg,
    onBackground = TextPrimary,
    surface = NavySurface,
    onSurface = TextPrimary,
    surfaceVariant = NavySurfaceHigh,
    onSurfaceVariant = TextMuted,
    outline = OutlineNavy,
    outlineVariant = OutlineNavy
)

@Composable
fun AquaAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AquaDarkColorScheme,
        typography = Typography,
        content = content
    )
}
