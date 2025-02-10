package ui.component.style

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div


@Composable
fun Padding(
    value: CSSpxValue = 0.px,
    content: @Composable () -> Unit
) {
    Padding(value, value, value, value, content)
}

@Composable
fun Padding(
    horizontal: CSSpxValue = 0.px,
    vertical: CSSpxValue = 0.px,
    content: @Composable () -> Unit
) {
    Padding(horizontal, horizontal, vertical, vertical, content)
}

@Composable
fun Padding(
    left: CSSpxValue = 0.px,
    right: CSSpxValue = 0.px,
    top: CSSpxValue = 0.px,
    bottom: CSSpxValue = 0.px,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            CssProperty.Display.Flex(this)
            CssProperty.Padding(left, right, top, bottom)(this)
        }
    }) {
        content()
    }
}

