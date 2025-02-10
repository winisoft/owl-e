package ui.component.oelement

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import preview.component.button.ControlPanel
import ui.component.img.Icons
import ui.component.style.Padding

@Composable
fun OElementHeader(
    icon: Icons,
    text: @Composable () -> Unit,
    controlPanel: @Composable (isHovered: Boolean) -> Unit,
    onItemClick: () -> Unit = {},
    onMouseDown: () -> Unit = {},
    onMouseUp: () -> Unit = {},
    onMouseOver: () -> Unit = {}
) {
    var isHovered by remember { mutableStateOf(false) }

    Div(attrs = {
        //classes("oelement-header")
        onClick { onItemClick() }
        onMouseOut { isHovered = false }
        onMouseDown { onMouseDown() }
        onMouseUp { onMouseUp() }
        onMouseOver {
            onMouseOver()
            isHovered = true
        }
    }) {
        // Left-aligned content: Icon and Text
        Div({
            classes("flex", "items-center", "gap-2")
        }) {
            Padding(0.px, 6.px, 0.px, 0.px) {
                icon()
            }
            text()
        }

        // Right-aligned control panel
        ControlPanel(isHovered) {
            controlPanel(isHovered)
        }
    }
}
//
//@Composable
//fun OElementHeader(
//    icon: Icons,
//    text: @Composable () -> Unit,
//    controlPanel: @Composable (isHovered: Boolean) -> Unit,
//    onItemClick: () -> Unit = {},
//    onMouseDown: () -> Unit = {},
//    onMouseUp: () -> Unit = {},
//    onMouseOver: () -> Unit = {}
//) {
//    var isHovered by remember { mutableStateOf(false) }
//
//    Div(attrs = {
//        classes("oelement-header")
//        onClick { onItemClick() }
//        onMouseOut { isHovered = false }
//        onMouseDown { onMouseDown() }
//        onMouseUp { onMouseUp() }
//        onMouseOver {
//            onMouseOver()
//            isHovered = true
//        }
//    }) {
//        Padding(0.px, 6.px, 0.px, 0.px) {
//            icon()
//        }
//
//        text()
//        ControlPanel(isHovered) {
//            controlPanel(isHovered)
//        }
//    }
//}
