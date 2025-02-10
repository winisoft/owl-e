package com.wini.owle.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class OwleDimensions(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 12.dp,
    val paddingLarge: Dp = 24.dp
)

/**
 * Stores and provides the app theme's dimensions
 */
internal val LocalDimensions = staticCompositionLocalOf { OwleDimensions() }