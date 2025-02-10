package ui.component.style

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

enum class Elevation(val boxShadow: String) {
    None("none"),
    Low("0px 1px 2px rgba(0, 0, 0, 0.12), 0px 1px 3px rgba(0, 0, 0, 0.24)"),
    Medium("0px 3px 6px rgba(0, 0, 0, 0.16), 0px 3px 6px rgba(0, 0, 0, 0.23)"),
    High("0px 10px 20px rgba(0, 0, 0, 0.19), 0px 6px 6px rgba(0, 0, 0, 0.23)");
}

@Composable
fun Elevated(
    elevation: Elevation = Elevation.None,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            property("box-shadow", elevation.boxShadow)
        }
    }) {
        content()
    }
}
