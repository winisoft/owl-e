package ui.component.style

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSBuilder
import org.jetbrains.compose.web.css.CSSStyleRuleBuilder
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Style
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
@Composable
fun Hoverable(
    defaultStyle: CSSStyleRuleBuilder.() -> Unit,
    hoverStyle: CSSStyleRuleBuilder.() -> Unit,
    content: @Composable () -> Unit
) {
    val hoverClass = "hoverable-${Uuid.random()}"
    Style {
        ".$hoverClass:hover" {
            hoverStyle()
        }
        ".$hoverClass" {
            defaultStyle()
        }
    }
    Div(attrs = {
        classes(hoverClass)
    }) {
        content()
    }
}