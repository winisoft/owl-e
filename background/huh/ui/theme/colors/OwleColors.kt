package com.wini.owle.ui.theme.colors

import androidx.compose.material.Colors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color


/**
 * an instance of CompositionLocal that implicitly passes color attributes through the composition
 * tree of our UI, and updates the entire composition lambda once some value changes
 */
internal val LocalColors = staticCompositionLocalOf { themedColors() }

/**
 * Allows access to the theme colors by maintaining the current theme state
 */
class OwleColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    background: Color,
    surface: Color,
    textPrimary: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    code: CodeColors,
    isLight: Boolean
) {
    /**
     * The color displayed most frequently
     */
    var primary by mutableStateOf(primary)
        private set

    /**
     * Used to distinguish two elements of the app using the primary color, such as the top app bar and the system bar.
     */
    var primaryVariant by mutableStateOf(primaryVariant)
        private set

    /**
     * Provides more ways to accent and distinguish text; useful for purposes such as floating action buttons,
        checkboxes, radio buttons, highlighting text, Links, and headlines
     */
    var secondary by mutableStateOf(secondary)
        private set

    /**
     *  Distinguishes two elements of the app using the secondary color
     */
    var secondaryVariant by mutableStateOf(secondaryVariant)
        private set

    /**
     * Appears behind scrollable content.
     */
    var background by mutableStateOf(background)
        private set

    /**
     * Used on surfaces of components, such as cards, sheets and menus
     */
    var surface by mutableStateOf(surface)
        private set

    /**
     * Most frequently used text color
     */
    var textPrimary by mutableStateOf(textPrimary)
        private set

    /**
     * Used to indicate error within components, such as text fields
     */
    var error by mutableStateOf(error)
        private set

    /**
     * Used for text and icons displayed on top of the primary color
     */
    var onPrimary by mutableStateOf(onPrimary)
        private set

    /**
     * Used for text and icons displayed on top of the secondary color
     */
    var onSecondary by mutableStateOf(onSecondary)
        private set

    /**
     * Used for text and icons displayed on top of the background color
     */
    var onBackground by mutableStateOf(onBackground)
        private set

    /**
     * Used for text and icons displayed on top of the surface color
     */
    var onSurface by mutableStateOf(onSurface)
        private set

    /**
     * Contains the colors used in code examples
     */
    var code by mutableStateOf(code)
        private set

    /**
     * Represents the theme's 'light' or 'dark' mode.
     */
    var isLight by mutableStateOf(isLight)
        internal set

    val header by mutableStateOf(Color(0xFFFFC66D))

    val text: Text
        get() = Text(this)

    inner class Text(colors: OwleColors) {
        val hyperLink: Color = colors.secondary
    }

    fun copy(
        primary: Color = this.primary,
        primaryVariant: Color = this.primaryVariant,
        secondary: Color = this.secondary,
        secondaryVariant: Color = this.secondaryVariant,
        background: Color = this.background,
        surface: Color = this.surface,
        textPrimary: Color = this.textPrimary,
        error: Color = this.error,
        onPrimary: Color = this.onPrimary,
        onSecondary: Color = this.onSecondary,
        onBackground: Color = this.onBackground,
        onSurface: Color = this.onSurface,
        code: CodeColors = this.code,
        isLight: Boolean = false
    ): OwleColors = OwleColors(
        primary = primary,
        primaryVariant = primaryVariant,
        secondary = secondary,
        secondaryVariant = secondaryVariant,
        background = background,
        surface = surface,
        textPrimary = textPrimary,
        error,
        onPrimary,
        onSecondary,
        onBackground,
        onSurface,
        code,
        isLight
    )

    fun updateColorsFrom(other: OwleColors) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        secondaryVariant = other.secondaryVariant
        background = other.background
        surface = other.surface
        textPrimary = other.textPrimary
        error = other.error
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        onBackground = other.onBackground
        onSurface = other.onSurface
        code = other.code
        isLight = other.isLight
    }

    val asMaterialColors = Colors(
        primary,
        primaryVariant,
        secondary,
        secondaryVariant,
        background,
        surface,
        error,
        onPrimary,
        onSecondary,
        onBackground,
        onSurface,
        Color.White,
        isLight
    )
}