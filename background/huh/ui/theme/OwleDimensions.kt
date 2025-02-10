package com.wini.owle.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class OwleDimens(
    var _paddingSmall: Dp = 4.dp,
    var _paddingMedium: Dp = 12.dp,
    var _paddingLarge: Dp = 24.dp
) {

    var paddingSmall by mutableStateOf(_paddingSmall)
        private set

    var paddingMedium by mutableStateOf(_paddingMedium)
        private set

    var paddingLarge by mutableStateOf(_paddingLarge)
        private set

    fun updateDimensFrom(other: OwleDimens) {
        paddingSmall = other._paddingSmall
        paddingMedium = other._paddingMedium
        paddingLarge = other._paddingLarge
    }
}

/**
 * Stores and provides the app theme's dimensions
 */
internal val LocalDimensions = staticCompositionLocalOf { OwleDimens() }