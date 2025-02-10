package ui.component.row

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div


@Composable
fun ItemRow(text: @Composable () -> Unit) {
    Div(attrs = { classes("row-base") }) {
        Div(attrs = { classes("row-text") }) {
            text()
        }
    }
}

