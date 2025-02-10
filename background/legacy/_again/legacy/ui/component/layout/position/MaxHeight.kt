package ui.component.layout.position

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Div


@Composable
fun MaxHeight(
    maxHeight: CSSNumeric = 100.percent,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("max-height", maxHeight)
        }
    }) {
        content()
    }
}
