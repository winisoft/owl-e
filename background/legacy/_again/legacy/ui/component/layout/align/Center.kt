package ui.component.layout.align

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

/**
 * Supports an alternative to Compose foundation's Modifier.align()
 */
@Composable
fun Center(
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("display", "flex")
            property("justify-content", "center")
            property("align-items", "center")
        }
    }) {
        content()
    }
}