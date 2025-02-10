package com.wini.owle.ui.theme.colors

import androidx.compose.ui.graphics.Color


val colorOwlePrimary = Color(0xFF61AFFE)
val colorOwleSecondary = Color(0xFF60DDA1)
val colorOwleBackground = Color(0xFF2B2B2B)
val colorOwleSurface = Color(0xFF1E1E1E)

val colorOwleTertiary = Color(0xFF9747FF)
val infoOwleInfo = Color(0xFFEBA781)

val colorOwlePrimaryVariant = Color(0xFF364135)
val colorOwleSecondaryVariant = Color(0xFF80BFFF)
val colorOwleTextPrimary = Color(0xFFA9B7C6)
val colorOwleError = Color(0xFF9E2927)
val colorOwleOnPrimary = Color(0xFFFFFFFF)
val colorOwleOnSecondary = Color(0xFFFFFFFF)
val colorOwleOnBackground = Color(0xFFA9B7C6)
val colorOwleOnSurface = Color(0xFFA9B7C6)


fun themedColors(
    primary: Color = colorOwlePrimary,
    primaryVariant: Color = colorOwlePrimaryVariant,
    secondary: Color = colorOwleSecondary,
    secondaryVariant: Color = colorOwleSecondaryVariant,
    background: Color = colorOwleBackground,
    surface: Color = colorOwleSurface,
    textPrimary: Color = colorOwleTextPrimary,
    error: Color = colorOwleError,
    onPrimary: Color = colorOwleOnPrimary,
    onSecondary: Color = colorOwleOnSecondary,
    onBackground: Color = colorOwleOnBackground,
    onSurface: Color = colorOwleOnSurface,
    code: CodeColors = CodeColors(),
    isLight: Boolean = true
): OwleColors = OwleColors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    textPrimary,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    code,
    isLight
)