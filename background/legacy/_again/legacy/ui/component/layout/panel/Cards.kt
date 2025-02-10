package ui.component.layout.panel

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import ui.component.layout.align.Alignment
import ui.component.style.Border
import ui.component.style.CssProperty
import ui.theme.OwleTheme


@Composable
fun OwleCard(
    bgColor: OwleTheme.ColorValue,
    borderColor: OwleTheme.ColorValue,
    bgColorHover: OwleTheme.ColorValue,
    borderColorHover: OwleTheme.ColorValue,
    content: @Composable () -> Unit
) {

    var isHovered by remember { mutableStateOf(false) }

    Div({
        classes(
            "workflow-card",
            "shadow-lg",
            "rounded-lg",
            if (isHovered) bgColor.bgClass else bgColorHover.bgClass
        )
        style {
            CssProperty.Display.Flex(this)
            CssProperty.FlexDirection.Column(this)
            CssProperty.JustifyContent(Alignment.Horizontal.Start)(this)
            CssProperty.Padding(1.cssRem, 1.cssRem, 1.cssRem, 1.cssRem)(this)
        }
        onMouseOver { isHovered = true }
        onMouseOut { isHovered = false }
    }) {
        Border(color = if (isHovered) borderColorHover else borderColor, width = 1.cssRem) {
            content()
        }
    }
}

@Composable
fun SurfaceCard(
    content: @Composable () -> Unit
) {
    OwleCard(
        bgColor = OwleTheme.Color.Surface,
        borderColor = OwleTheme.Color.Surface.CardBorder,
        bgColorHover = OwleTheme.Color.Surface.CardBackgroundHovered,
        borderColorHover = OwleTheme.Color.Surface.CardBorderHovered
    ) {
        content()
    }
}

@Composable
fun SurfaceTier2Card(
    content: @Composable () -> Unit
) {
    OwleCard(
        bgColor = OwleTheme.Color.SurfaceTier2,
        borderColor = OwleTheme.Color.SurfaceTier2.CardBorder,
        bgColorHover = OwleTheme.Color.SurfaceTier2.CardBackgroundHovered,
        borderColorHover = OwleTheme.Color.SurfaceTier2.CardBorderHovered
    ) {
        content()
    }
}

@Composable
fun SurfaceVariantCard(
    content: @Composable () -> Unit
) {
    OwleCard(
        bgColor = OwleTheme.Color.SurfaceVariant,
        borderColor = OwleTheme.Color.SurfaceVariant.CardBorder,
        bgColorHover = OwleTheme.Color.SurfaceVariant.CardBackgroundHovered,
        borderColorHover = OwleTheme.Color.SurfaceVariant.CardBorderHovered
    ) {
        content()
    }
}

@Composable
fun PhaseTier1Card(
    content: @Composable () -> Unit
) {
    OwleCard(
        bgColor = OwleTheme.Color.Primary,
        borderColor = OwleTheme.Color.Primary.CardBorder,
        bgColorHover = OwleTheme.Color.Primary.CardBackgroundHovered,
        borderColorHover = OwleTheme.Color.Primary.CardBorderHovered
    ) {
        content()
    }
}

@Composable
fun PhaseTier2Card(
    content: @Composable () -> Unit
) {
    OwleCard(
        bgColor = OwleTheme.Color.Secondary,
        borderColor = OwleTheme.Color.Secondary.CardBorder,
        bgColorHover = OwleTheme.Color.Secondary.CardBackgroundHovered,
        borderColorHover = OwleTheme.Color.Secondary.CardBorderHovered
    ) {
        content()
    }
}

@Composable
fun PhaseTier3Card(
    content: @Composable () -> Unit
) {
    OwleCard(
        bgColor = OwleTheme.Color.Tertiary,
        borderColor = OwleTheme.Color.Tertiary.CardBorder,
        bgColorHover = OwleTheme.Color.Tertiary.CardBackgroundHovered,
        borderColorHover = OwleTheme.Color.Tertiary.CardBorderHovered
    ) {
        content()
    }
}

@Composable
fun PhaseTier4Card(
    content: @Composable () -> Unit
) {
    OwleCard(
        bgColor = OwleTheme.Color.Tertiary,
        borderColor = OwleTheme.Color.Tertiary.CardBorder,
        bgColorHover = OwleTheme.Color.Tertiary.CardBackgroundHovered,
        borderColorHover = OwleTheme.Color.Tertiary.CardBorderHovered
    ) {
        content()
    }
}
