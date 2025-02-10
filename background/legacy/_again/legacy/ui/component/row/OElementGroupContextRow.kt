package ui.component.row

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.whiteSpace
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import ui.component.img.Icon
import ui.component.img.Icons

@Composable
fun OElementGroupContextRow(
    rowSubjectIcon: Icons,
    rowSubjectText: String,
    groupContextIcon: Icons,
    groupContextText: String
) {

    Div(
        attrs = {
            classes("flex", "items-center", "pl-4", "py-2")
        }
    ) {
        Div(attrs = { classes("flex-none", "pr-2") }) {

        }

        Div {

        }
    }
    P({ classes("text-l", "text-white") }) { Text("in") }

    Div(
        attrs = {
            classes("flex", "items-center", "w-full", "pl-2", "py-2")
        }
    ) {
        Div(attrs = { classes("flex-none", "pr-2") }) {

        }

        Div {

        }
    }
}