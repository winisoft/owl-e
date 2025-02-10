package ui.component.layout.panel

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ui.theme.OwleTheme


@OptIn(ExperimentalComposeWebApi::class)
@Composable
fun LeftPanel(content: @Composable () -> Unit) {
    Div({
        classes("left-panel", OwleTheme.Color.Background.bgClass)
        style {
            width(20.vw)
            height(100.vh)
            transitions {
                properties("width")
                defaultDuration(0.3.s)
                defaultTimingFunction(AnimationTimingFunction.Ease)
            }
        }
    }) {
        content()
    }
}
