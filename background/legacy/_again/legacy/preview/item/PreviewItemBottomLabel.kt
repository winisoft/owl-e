package preview.item

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun PreviewItemBottomLabel(label: String) {
    Div(
        attrs = {
            classes("text-center", "text-sm", "font-medium", "text-white", "h-12")
        }
    ) {
        Text(label)
    }
}