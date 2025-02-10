package com.wini.owle.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.wini.owle.ui.theme.colors.darkScheme1
import androidx.compose.material.Typography


object OwleTheme {

//    val colors: OwleColorScheme
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalColors.current
//
//    val typography: Typography
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalTypography.current
//
//    val dimensions: OwleDimensions
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalDimensions.current
}


//@Composable
//fun OwleTheme.materialTheme(content: @Composable () -> Unit ) {
//    MaterialTheme(
//        typography = LocalTypography.current,
//        colorScheme = LocalColors.current.asColorScheme,
//        content = content
//    )
//}

/**
 * Wraps content lambda with a CompositionLocalProvider. It will tell LocalColors, LocalDimensions and LocalTypography
 * to provide the styling attributes you pass to the theme function into the app's composable tree
 */
@Composable
fun OwleTheme(
    colors: Colors = darkScheme1,
    typography: Typography = OwleTypography,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = darkScheme1,
        typography = typography ,
        content = content
    )

    // creating a new object for colors to not mutate the initial colors set when updating the values
//    val rememberedColors = remember {
//        OwleColorScheme(colorScheme.asColorScheme, isLightTheme)
//    }.apply {
//        colors.updateColorsFrom(other = asColorScheme)
//    }
//
//    CompositionLocalProvider(
//        LocalColors provides rememberedColors,
//        LocalDimensions provides dimensions,
//        LocalTypography provides typography
//    ) {
//        OwleTheme.materialTheme {
//            content()
//        }
//    }
}