package ui.component.button

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import ui.component.img.Icon
import ui.component.img.Icons


@Composable
fun LeftPanelExpandedBtn(
    isPanelExpanded: Boolean = false,
    onToggle: () -> Unit
){

    Div(attrs = {
        classes("panel-expanded-button", if (isPanelExpanded) "expanded" else  "collapsed")
        onClick { onToggle() }
    }) {
        Icon(
            icon = if (isPanelExpanded) Icons.ChevronLeft else Icons.OwleExpandPanel,
            size = if (isPanelExpanded) 30.px else 128.px
        )
    }

}