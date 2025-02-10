package com.wini.owle.ui.theme.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

interface OwleFont {
    @Composable
    fun fontFamily(): FontFamily
}