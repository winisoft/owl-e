package ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ui.util.theme.OwleColors


@Composable
fun RightPanel(content: @Composable () -> Unit) {
    Div(attrs = {
        classes("right-panel")
        style {
            flexGrow(1)
            height(100.vh)
            backgroundColor(OwleColors.BackgroundTier2)
        }
    }) {
        content()
    }
}
