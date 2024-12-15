package com.example.expensetracker.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

@Composable
fun ExpenseTrackerTheme(content: @Composable () -> Unit) {
    val darkTheme = ThemeState.isDarkTheme.value // Čtení stavu motivu
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Purple40,
            onPrimary = White,
            surface = Black,
            onSurface = White
        )
    } else {
        lightColorScheme(
            primary = Purple80,
            onPrimary = Black,
            surface = White,
            onSurface = Black
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
