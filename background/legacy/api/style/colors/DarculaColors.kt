package com.wini.owle.air.api.style.colors

import androidx.compose.ui.graphics.Color
import com.gm.lo.desktop.ui.style.colors.LoColors


private val colorDarculaPrimary = Color(0xFF155221)
private val colorDarculaPrimaryVariant = Color(0xFF364135)
private val colorDarculaSecondary = Color(0xFF287BDE)
private val colorDarculaSecondaryVariant = Color(0xFF80BFFF)
private val colorDarculaBackground = Color(0xFF2B2B2B)
private val colorDarculaSurface = Color(0xFF3A3A3A)
private val colorDarculaTextPrimary = Color(0xFFA9B7C6)
private val colorDarculaError = Color(0xFF9E2927)
private val colorDarculaOnPrimary = Color(0xFFFFFFFF)
private val colorDarculaOnSecondary = Color(0xFFFFFFFF)
private val colorDarculaOnBackground = Color(0xFFA9B7C6)
private val colorDarculaOnSurface = Color(0xFFA9B7C6)


fun darculaColors(
    primary: Color = colorDarculaPrimary,
    primaryVariant: Color = colorDarculaPrimaryVariant,
    secondary: Color = colorDarculaSecondary,
    secondaryVariant: Color = colorDarculaSecondaryVariant,
    background: Color = colorDarculaBackground,
    surface: Color = colorDarculaSurface,
    textPrimary: Color = colorDarculaTextPrimary,
    error: Color = colorDarculaError,
    onPrimary: Color = colorDarculaOnPrimary,
    onSecondary: Color = colorDarculaOnSecondary,
    onBackground: Color = colorDarculaOnBackground,
    onSurface: Color = colorDarculaOnSurface,
    code: CodeColors = CodeColors(),
    isLight: Boolean = true
): LoColors = LoColors(
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