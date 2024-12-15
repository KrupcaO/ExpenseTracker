package com.example.expensetracker.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

object ThemeState {
    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> get() = _isDarkTheme

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}
