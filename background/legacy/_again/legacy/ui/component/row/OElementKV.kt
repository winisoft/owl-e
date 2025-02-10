package ui.component.row

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import ui.component.img.Icon
import ui.component.img.Icons

@Composable
fun OElementKV(icon: Icons, key: String, value: String) {
    Div(attrs = { classes("kv-container") }) {
        Div(attrs = { classes("row-icon") }) {
            Icon(icon)
        }
        Div(attrs = { classes("kv-key") }) {
            Text(key)
        }
        Div(attrs = { classes("kv-value") }) {
            Text(value)
        }
    }
}