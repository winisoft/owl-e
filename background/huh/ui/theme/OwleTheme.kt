package com.wini.owle.ui.theme

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
import androidx.compose.ui.text.font.FontFamily
import com.wini.owle.ui.theme.colors.OwleColors
import com.wini.owle.ui.theme.colors.LocalColors
import com.wini.owle.ui.theme.font.JetbrainsMono
import org.jetbrains.compose.resources.ExperimentalResourceApi


object OwleTheme {

    val colors: OwleColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: OwleTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions: OwleDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current


    @Composable
    fun updateColorsFrom(colors: OwleColors): OwleColors {
        colors.updateColorsFrom(colors)
        return OwleTheme.colors
    }

    @Composable
    fun updateTypographyFrom(typography: OwleTypography): OwleTypography {
        typography.updateTypographyFrom(typography.toTypography())
        return OwleTheme.typography
    }

    @Composable
    fun updateDimensFrom(dimensions: OwleDimens): OwleDimens {
        dimensions.updateDimensFrom(dimensions)
        return OwleTheme.dimensions
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OwleTheme.materialTheme(content: @Composable () -> Unit ) {

    MaterialTheme(
        colors = Colors(
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
            onError = Color.White,
            isLight = true
        ),
        typography = Typography(
//            defaultFontFamily = JetbrainsMono.fontFamily(),
//            h1 = typography.h1,
//            h2 = typography.h2,
//            h3 = typography.h3,
//            h4 = typography.h4,
//            h5 = typography.h5,
//            h6 = typography.h6,
//            body1 = typography.body1,
//            body2 = typography.body2,
//            subtitle1 = typography.subtitle1,
//            subtitle2 = typography.subtitle2,
//            button = typography.button,
//            caption = typography.caption,
//            overline = TextStyle.Default
        ),
        shapes = Shapes(),
        content = content
    )
}

/**
 * Wraps content lambda with a CompositionLocalProvider. It will tell LocalColors, LocalDimensions and LocalTypography
 * to provide the styling attributes you pass to the theme function into the app's composable tree
 */
@Composable
fun OwleTheme(
    colors: OwleColors = OwleTheme.colors,
    typography: OwleTypography = OwleTheme.typography,
    dimensions: OwleDimens = OwleTheme.dimensions,
    content: @Composable () -> Unit
) {

    val colorsCopy = OwleTheme.updateColorsFrom(colors)
    // creating a new object for colors to not mutate the initial colors set when updating the values
    val rememberedColors = remember { colorsCopy }

    val typographyCopy = OwleTheme.updateTypographyFrom(typography)
    val rememberedTypography = remember {
        typographyCopy
    }

    val dimensCopy = OwleTheme.updateDimensFrom(dimensions)
    val rememberedDimens = remember {
        dimensCopy
    }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalDimensions provides rememberedDimens,
        LocalTypography provides rememberedTypography
    ) {
        OwleTheme.materialTheme {
            content()
        }
    }
}