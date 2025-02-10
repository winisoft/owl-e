package com.wini.owle.air.api.style

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.gm.lo.desktop.ui.style.colors.LoColors
import com.gm.lo.desktop.ui.style.colors.LocalColors

//object LoTheme {
//
//    val colors: LoColors
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalColors.current
//
//    val typography: LoTypography
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalTypography.current
//
//    val dimensions: LoDimensions
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalDimensions.current
//}
//
//@Composable
//fun LoTheme.materialTheme(content: @Composable () -> Unit ) {
//    MaterialTheme(
//        colors = colors.run { Colors(
//            primary = primary,
//            primaryVariant = primaryVariant,
//            secondary = secondary,
//            secondaryVariant = secondaryVariant,
//            background = background,
//            surface = surface,
//            error = error,
//            onPrimary = onPrimary,
//            onSecondary = onSecondary,
//            onBackground = onBackground,
//            onSurface = onSurface,
//            onError = Color.White,
//            isLight = true
//        )
//        },
//        typography = typography.run {
//            Typography(
//                defaultFontFamily = Rubik.fontFamily,
//                h1 = h1,
//                h2 = h2,
//                h3 = h3,
//                h4 = h4,
//                h5 = h5,
//                h6 = h6,
//                subtitle1 = subtitle,
//                subtitle2 = subtitle,
//                body1 = body,
//                body2 = body,
//                button = button,
//                caption = caption,
//                overline = TextStyle.Default
//            )
//        },
//        shapes = Shapes(),
//        content = content
//    )
//}

///**
// * Wraps content lambda with a CompositionLocalProvider. It will tell LocalColors, LocalDimensions and LocalTypography
// * to provide the styling attributes you pass to the theme function into the app's composable tree
// */
//@Composable
//fun LoTheme(
//    colors: LoColors = LoTheme.colors,
//    typography: LoTypography = LoTheme.typography,
//    dimensions: LoDimensions = LoTheme.dimensions,
//    content: @Composable () -> Unit
//) {
//
//    // creating a new object for colors to not mutate the initial colors set when updating the values
//    val rememberedColors = remember {
//        colors.copy()
//    }.apply {
//        updateColorsFrom(colors)
//    }
//
//    CompositionLocalProvider(
//        LocalColors provides rememberedColors,
//        LocalDimensions provides dimensions,
//        LocalTypography provides typography
//    ) {
//        LoTheme.materialTheme {
//            content()
//        }
//    }
//}