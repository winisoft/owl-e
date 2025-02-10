package ui.component.layout.position

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

/**
 * Supports an alternative to Compose foundation's Modifier.fillMaxHeight()
 */
@Composable
fun FillMaxHeight(
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("height", "100%")
        }
    }) {
        content()
    }
}

@Composable
fun FillHeight(
    percent: Int,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("height", "$percent%")
        }
    }) {
        content()
    }
}