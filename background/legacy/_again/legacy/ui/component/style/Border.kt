package ui.component.style

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import ui.theme.OwleTheme

/**
 * Supports an alternative to Compose foundation's Modifier.border()
 */
@Composable
fun Border(
    color: OwleTheme.ColorValue,
    width: CSSNumeric = 1.px,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("border", "$width solid ${color.value}")
        }
    }) {
        content()
    }
}
