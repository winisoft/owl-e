package ui.component.layout.position

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div


/**
 * Supports an alternative to Compose foundation's Modifier.fillMaxWidth()
 */
@Composable
fun FillMaxWidth(
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("width", "100%")
        }
    }) {
        content()
    }
}

@Composable
fun FillWidth(
    percent: Int,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("width", "$percent%")
        }
    }) {
        content()
    }
}
