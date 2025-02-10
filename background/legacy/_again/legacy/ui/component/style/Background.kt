package ui.component.style

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

/**
 * Supports an alternative to Compose foundation's Modifier.background()
 */
@Composable
fun Background(
    color: CSSColorValue,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("background-color", color.toString())
        }
    }) {
        content()
    }
}


