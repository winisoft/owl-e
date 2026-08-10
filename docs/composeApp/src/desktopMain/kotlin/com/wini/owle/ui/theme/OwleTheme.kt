package com.wini.owle.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import com.wini.owle.ui.theme.colors.darkScheme1
import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.wini.owle.ui.theme.font.Rubik
import com.wini.owle.ui.util.scrolled.RootBackground
import com.wini.owle.ui.util.scrolled.ScrollCardinalities


@Composable
fun OwleTypography(fontFamily: FontFamily = Rubik()): Typography {
    return remember(fontFamily) {
        Typography(
            defaultFontFamily = fontFamily,
            body1 = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontFamily = fontFamily),
            body2 = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontFamily = fontFamily),
            h1 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 24.sp),
            h2 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 24.sp),
            h3 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 24.sp),
            h4 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 24.sp),
            h5 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 24.sp),
            h6 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp),
            button = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
            subtitle1 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
            subtitle2 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
            caption = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp),
            overline = TypographyStyles.bodyStyle(fontFamily = fontFamily, textDecoration = TextDecoration.Underline)
        )
    }
}


internal val LocalTypography = staticCompositionLocalOf<Typography> {
    error("No Typography provided")
}

internal val LocalColors = staticCompositionLocalOf { darkScheme1 }

object OwleTheme {

    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions: OwleDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current
}


@Composable
fun OwleTheme.materialTheme(content: @Composable () -> Unit ) {
    MaterialTheme(
        typography = LocalTypography.current,
        colors = LocalColors.current
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(OwleTheme.colors.background)
        ) {
            content()
        }
    }
}

/**
 * Wraps content lambda with a CompositionLocalProvider. It will tell LocalColors, LocalDimensions and LocalTypography
 * to provide the styling attributes you pass to the theme function into the app's composable tree
 */
@Composable
fun OwleTheme(
    colors: Colors = darkScheme1,
    dimensions: OwleDimensions = OwleDimensions(),
    isLightTheme: Boolean = false,
    fontFamily: @Composable () -> FontFamily = { Rubik() },
    content: @Composable () -> Unit
) {
    val typography = OwleTypography(fontFamily())

    // Ensure colors are remembered and immutable
    val rememberedColors = remember(colors, isLightTheme) {
        Colors(
            primary = colors.primary,
            primaryVariant = colors.primaryVariant,
            secondary = colors.secondary,
            secondaryVariant = colors.secondaryVariant,
            background = colors.background,
            surface = colors.surface,
            error = colors.error,
            onPrimary = colors.onPrimary,
            onSecondary = colors.onSecondary,
            onBackground = colors.onBackground,
            onSurface = colors.onSurface,
            onError = colors.onError,
            isLight = isLightTheme
        )
    }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography
    ) {
        MaterialTheme(
            colors = rememberedColors,
            typography = typography
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(rememberedColors.background)
            ) {
                content()
            }
        }
    }
}