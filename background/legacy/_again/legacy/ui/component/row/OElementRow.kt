package ui.component.row

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import ui.component.img.Icon
import ui.component.img.Icons


@Composable
fun OElementRow(icon: Icons, text: @Composable () -> Unit) {
    Div(attrs = { classes("row-base") }) {
        Div(attrs = { classes("row-icon") }) {
            Icon(icon)
        }
        Div(attrs = { classes("row-text") }) {
            text()
        }
    }
}
