package ui.component.oelement.folder

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import ui.component.img.Icons
import ui.component.row.OElementRow
import ui.component.text.OText
import ui.component.text.OwleText


@Composable
fun FolderStat(
    icon: Icons,
    text: String
) {

    OElementRow(
        text = { OwleText(text, OText.Size.LG) },
        icon = icon
    )

}