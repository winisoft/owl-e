package preview.component.button

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import ui.component.layout.align.Arrangement
import ui.component.layout.group.OwleRow


@Composable
fun ControlPanel(isHovered: Boolean, content: @Composable () -> Unit) {
    Div({
        classes("control-panel")
        if (isHovered) {
            classes("is-hovered")
        }
    }) {
        content()
    }
}
