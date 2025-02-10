package ui.component.row

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.pc
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import ui.component.img.Icon
import ui.component.img.Icons
import ui.component.layout.align.Arrangement
import ui.component.layout.group.OwleBox
import ui.component.layout.group.OwleRow
import ui.component.layout.position.FillMaxWidth
import ui.component.oelement.OElementIcon
import ui.component.text.ItemRowText
import ui.component.text.OwleText
import ui.theme.OwleTheme


@Composable
fun OElementPanel(
    icon: Icons,
    text: @Composable () -> Unit,
    panelContent: @Composable () -> Unit
) {

    PanelContainer {

        OwleRow {

            OElementIcon {
                Icon(icon)
            }

            OwleBox(
                horizontalArrangement = Arrangement.Start
            ) {
                text()
            }

            panelContent()
        }
    }
}

