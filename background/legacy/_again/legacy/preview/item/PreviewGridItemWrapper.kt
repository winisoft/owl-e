package preview.item

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div


@Composable
fun PreviewGridItemWrapper(content: @Composable () -> Unit) {
    Div(attrs = { classes("flex", "justify-center", "items-center") }) {
        content()
    }
}