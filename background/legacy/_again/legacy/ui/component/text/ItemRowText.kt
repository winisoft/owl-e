package ui.component.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

@Composable
fun ItemRowText(content: @Composable () -> Unit) {

    Div({
        //classes("row-text")
    }) {
        content()
    }

}