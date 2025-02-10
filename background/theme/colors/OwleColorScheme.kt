package com.wini.owle.ui.theme.colors
//
//import androidx.compose.material3.ColorScheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.runtime.*
//import androidx.compose.ui.graphics.Color
//
//
//
///**
// * Allows access to the theme colors by maintaining the current theme state
// */
//class OwleColorScheme(
//    primary: Color,
//    onPrimary: Color,
//    primaryContainer: Color,
//    onPrimaryContainer: Color,
//    inversePrimary: Color,
//    secondary: Color,
//    onSecondary: Color,
//    secondaryContainer: Color,
//    onSecondaryContainer: Color,
//    tertiary: Color,
//    onTertiary: Color,
//    tertiaryContainer: Color,
//    onTertiaryContainer: Color,
//    background: Color,
//    onBackground: Color,
//    surface: Color,
//    onSurface: Color,
//    surfaceVariant: Color,
//    onSurfaceVariant: Color,
//    surfaceTint: Color,
//    inverseSurface: Color,
//    inverseOnSurface: Color,
//    error: Color,
//    onError: Color,
//    errorContainer: Color,
//    onErrorContainer: Color,
//    outline: Color,
//    isLightTheme: Boolean = false
//) {
//
//    constructor(colorScheme: ColorScheme, isLightTheme: Boolean = false) : this (
//        primary = colorScheme.primary,
//        onPrimary = colorScheme.onPrimary,
//        primaryContainer = colorScheme.primaryContainer,
//        onPrimaryContainer = colorScheme.onPrimaryContainer,
//        inversePrimary = colorScheme.inversePrimary,
//        secondary = colorScheme.secondary,
//        onSecondary = colorScheme.onSecondary,
//        secondaryContainer = colorScheme.secondaryContainer,
//        onSecondaryContainer = colorScheme.onSecondaryContainer,
//        tertiary = colorScheme.tertiary,
//        onTertiary = colorScheme.onTertiary,
//        tertiaryContainer = colorScheme.tertiaryContainer,
//        onTertiaryContainer = colorScheme.onTertiaryContainer,
//        background = colorScheme.background,
//        onBackground = colorScheme.onBackground,
//        surface = colorScheme.surface,
//        onSurface = colorScheme.onSurface,
//        surfaceVariant = colorScheme.surfaceVariant,
//        onSurfaceVariant = colorScheme.onSurfaceVariant,
//        surfaceTint = colorScheme.surfaceTint,
//        inverseSurface = colorScheme.inverseSurface,
//        inverseOnSurface = colorScheme.inverseOnSurface,
//        error = colorScheme.error,
//        onError = colorScheme.onError,
//        errorContainer = colorScheme.errorContainer,
//        onErrorContainer = colorScheme.onErrorContainer,
//        outline = colorScheme.outline,
//        isLightTheme = isLightTheme
//    )
//
//    var primary by mutableStateOf(primary)
//        private set
//
//    var onPrimary by mutableStateOf(onPrimary)
//        private set
//
//    var primaryContainer by mutableStateOf(primaryContainer)
//        private set
//
//    var onPrimaryContainer by mutableStateOf(onPrimaryContainer)
//        private set
//
//    var inversePrimary by mutableStateOf(inversePrimary)
//        private set
//
//    var secondary by mutableStateOf(secondary)
//        private set
//
//    var onSecondary by mutableStateOf(onSecondary)
//        private set
//
//    var secondaryContainer by mutableStateOf(secondaryContainer)
//        private set
//
//    var onSecondaryContainer by mutableStateOf(onSecondaryContainer)
//        private set
//
//    var tertiary by mutableStateOf(tertiary)
//        private set
//
//    var onTertiary by mutableStateOf(onTertiary)
//        private set
//
//    var tertiaryContainer by mutableStateOf(tertiaryContainer)
//        private set
//
//    var onTertiaryContainer by mutableStateOf(onTertiaryContainer)
//        private set
//
//    var background by mutableStateOf(background)
//        private set
//
//    var onBackground by mutableStateOf(onBackground)
//        private set
//
//    var surface by mutableStateOf(surface)
//        private set
//
//    var onSurface by mutableStateOf(onSurface)
//        private set
//
//    var surfaceVariant by mutableStateOf(surfaceVariant)
//        private set
//
//    var onSurfaceVariant by mutableStateOf(onSurfaceVariant)
//        private set
//
//    var surfaceTint by mutableStateOf(surfaceTint)
//        private set
//
//    var inverseSurface by mutableStateOf(inverseSurface)
//        private set
//
//    var inverseOnSurface by mutableStateOf(inverseOnSurface)
//        private set
//
//    var error by mutableStateOf(error)
//        private set
//
//    var onError by mutableStateOf(onError)
//        private set
//
//    var errorContainer by mutableStateOf(errorContainer)
//        private set
//
//    var onErrorContainer by mutableStateOf(onErrorContainer)
//        private set
//
//    var outline by mutableStateOf(outline)
//        private set
//
//    var isLightTheme by mutableStateOf(isLightTheme)
//        private set
//
//    fun copy(
//        primary: Color,
//        onPrimary: Color,
//        primaryContainer: Color,
//        onPrimaryContainer: Color,
//        inversePrimary: Color,
//        secondary: Color,
//        onSecondary: Color,
//        secondaryContainer: Color,
//        onSecondaryContainer: Color,
//        tertiary: Color,
//        onTertiary: Color,
//        tertiaryContainer: Color,
//        onTertiaryContainer: Color,
//        background: Color,
//        onBackground: Color,
//        surface: Color,
//        onSurface: Color,
//        surfaceVariant: Color,
//        onSurfaceVariant: Color,
//        surfaceTint: Color,
//        inverseSurface: Color,
//        inverseOnSurface: Color,
//        error: Color,
//        onError: Color,
//        errorContainer: Color,
//        onErrorContainer: Color,
//        outline: Color
//    ): OwleColorScheme = OwleColorScheme(
//        primary = primary,
//        onPrimary = onPrimary,
//        primaryContainer = primaryContainer,
//        onPrimaryContainer = onPrimaryContainer,
//        inversePrimary = inversePrimary,
//        secondary = secondary,
//        onSecondary = onSecondary,
//        secondaryContainer = secondaryContainer,
//        onSecondaryContainer = onSecondaryContainer,
//        tertiary = tertiary,
//        onTertiary = onTertiary,
//        tertiaryContainer = tertiaryContainer,
//        onTertiaryContainer = onTertiaryContainer,
//        background = background,
//        onBackground = onBackground,
//        surface = surface,
//        onSurface = onSurface,
//        surfaceVariant = surfaceVariant,
//        onSurfaceVariant = onSurfaceVariant,
//        surfaceTint = surfaceTint,
//        inverseSurface = inverseSurface,
//        inverseOnSurface = inverseOnSurface,
//        error = error,
//        onError = onError,
//        errorContainer = errorContainer,
//        onErrorContainer = onErrorContainer,
//        outline = outline
//    )
//
//    fun updateColorsFrom(other: ColorScheme) {
//        primary = other.primary
//        onPrimary = other.onPrimary
//        primaryContainer = other.primaryContainer
//        onPrimaryContainer = other.onPrimaryContainer
//        inversePrimary = other.inversePrimary
//        secondary = other.secondary
//        onSecondary = other.onSecondary
//        secondaryContainer = other.secondaryContainer
//        onSecondaryContainer = other.onSecondaryContainer
//        tertiary = other.tertiary
//        onTertiary = other.onTertiary
//        tertiaryContainer = other.tertiaryContainer
//        onTertiaryContainer = other.onTertiaryContainer
//        background = other.background
//        onBackground = other.onBackground
//        surface = other.surface
//        onSurface = other.onSurface
//        surfaceVariant = other.surfaceVariant
//        onSurfaceVariant = other.onSurfaceVariant
//        surfaceTint = other.surfaceTint
//        inverseSurface = other.inverseSurface
//        inverseOnSurface = other.inverseOnSurface
//        error = other.error
//        onError = other.onError
//        errorContainer = other.errorContainer
//        onErrorContainer = other.onErrorContainer
//        outline = other.outline
//    }
//
//    val asColorScheme = ColorScheme(
//        primary,
//        onPrimary,
//        primaryContainer,
//        onPrimaryContainer,
//        inversePrimary,
//        secondary,
//        onSecondary,
//        secondaryContainer,
//        onSecondaryContainer,
//        tertiary,
//        onTertiary,
//        tertiaryContainer,
//        onTertiaryContainer,
//        background,
//        onBackground,
//        surface,
//        onSurface,
//        surfaceVariant,
//        onSurfaceVariant,
//        surfaceTint,
//        inverseSurface,
//        inverseOnSurface,
//        error,
//        onError,
//        errorContainer,
//        onErrorContainer,
//        outline
//    )
//}
//
///**
// * an instance of CompositionLocal that implicitly passes color attributes through the composition
// * tree of our UI, and updates the entire composition lambda once some value changes
// */
//internal val LocalColors = staticCompositionLocalOf {
//    OwleColorScheme(colorScheme = darkScheme, isLightTheme = false)
//}