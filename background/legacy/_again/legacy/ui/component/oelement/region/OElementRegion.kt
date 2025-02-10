package ui.component.oelement.region

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ui.theme.OwleTheme


@Composable
fun OElementRegion(
    backgroundColor: CSSColorValue = OwleTheme.Color.Surface,
    borderColor: CSSColorValue = OwleTheme.Color.Surface,
    borderWidth: CSSSizeValue<CSSUnit.px> = 2.px,
    minWidth: CSSSizeValue<CSSUnit.px> = 380.px,
    content: @Composable () -> Unit
) {

    Div({
        classes(
            "shadow-lg",
            "rounded-lg",
            "p-4",
            "flex",
            "items-center"
        )
        style {
            border(width = borderWidth, color = borderColor)
            backgroundColor(value = backgroundColor)
            minWidth(value = minWidth)
        }
    }) {
        content()
    }

}