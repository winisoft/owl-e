package com.wini.owle.air.api.style

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class LoDimensions(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 12.dp,
    val paddingLarge: Dp = 24.dp
)

/**
 * Stores and provides the app theme's dimensions
 */
internal val LocalDimensions = staticCompositionLocalOf { LoDimensions() }