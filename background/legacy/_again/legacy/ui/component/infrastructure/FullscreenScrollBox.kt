package ui.component.infrastructure

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

@Composable
fun FullscreenScrollBox(block: @Composable () -> Unit) {

    Div({ classes("overflow-y-auto", "h-screen") }){
        block()
    }

}