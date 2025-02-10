package ui.component.layout.group

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ui.component.layout.align.Alignment
import ui.component.layout.align.Arrangement
import ui.component.style.CssProperty


@Composable
fun OwleBox(
    horizontalArrangement: Arrangement = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.Vertical.Center,
    paddingL: CSSNumeric = 0.px,
    paddingR: CSSNumeric = 0.px,
    paddingT: CSSNumeric = 0.px,
    paddingB: CSSNumeric = 0.px,
    width: CSSNumeric? = null,
    height: CSSNumeric? = null,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        classes("owle-box")
        style {
            CssProperty.Display.Flex(this)
            CssProperty.JustifyContent(horizontalArrangement)(this)
            CssProperty.AlignItems(verticalAlignment)(this)
            CssProperty.Padding(paddingL, paddingR, paddingT, paddingB)(this)
            if (width != null)
                width(width)
            if (height != null)
                height(height)
        }
    }) {
        content()
    }
}


