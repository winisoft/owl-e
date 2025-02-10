package ui.component.row

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.Div
import ui.component.img.Icon
import ui.component.img.Icons


@Composable
fun OElementTextArea(
    text: @Composable () -> Unit,
    iconLeft: Icons,
    controlPanelButtons: List<@Composable () -> Unit>
) {
    var isHovered by remember { mutableStateOf(false) }

    Div({
        onMouseOver { isHovered = true }
        onMouseOut { isHovered = false }
        classes("relative", "flex", "items-center")
    }) {
        ItemRow(
            text = text
        )
    }
}