package ui.component.oelement

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

@Composable
fun OElementIcon(content: @Composable () -> Unit) {

    Div({
       //classes("panel-icon")
    }) {
        content()
    }

}