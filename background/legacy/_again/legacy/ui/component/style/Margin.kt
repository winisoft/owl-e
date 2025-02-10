package ui.component.style

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div


@Composable
fun Margin(
    margin: CSSNumeric = 0.px,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("margin", margin)
        }
    }) {
        content()
    }
}
