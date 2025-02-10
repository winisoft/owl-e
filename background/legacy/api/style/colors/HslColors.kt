package com.gm.lo.desktop.ui.style.colors

import androidx.annotation.IntRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.hsv
import androidx.compose.ui.graphics.ExperimentalGraphicsApi

@OptIn(ExperimentalGraphicsApi::class)
fun loColor(@IntRange(from = 0, to = 360L) hue: Int) = hsv(hue.toFloat(), 1f, .5f)