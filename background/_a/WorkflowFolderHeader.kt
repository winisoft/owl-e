package ui.component.oelement.folder

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.minHeight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import ui.component.button.IconButton
import ui.component.img.Icons
import ui.component.row.OElementPanel
import ui.component.text.OText
import ui.component.text.OwleText


@Composable
fun WorkflowFolderHeader(id: Long, name: String) {
    OElementPanel(
        text = {
            OwleText(
                name,
                OText.Size.XL,
                OText.Color.ON_SURFACE,
                OText.Style.BOLD
            ) {
                style {
                    minHeight(30.px)
                }
            }
        },
        icon = Icons.Folder,
        panelContent = {
            IconButton(icon = Icons.Clone, onClick = {})
            IconButton(icon = Icons.Junction, onClick = {})
        }
    )
}
