package com.wini.owle.ui.theme.colors

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color


@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)


@Immutable
data class ExtendedColorScheme(
    val informational: ColorFamily
    //informational: #EBA781
    //pythonModule: #33BBB0
    //pythonFunction: #DCDCAA
)
