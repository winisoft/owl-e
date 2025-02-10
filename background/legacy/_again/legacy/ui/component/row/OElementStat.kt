package ui.component.row

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.whiteSpace
import ui.component.img.Icon
import ui.component.img.Icons
import ui.component.layout.align.Arrangement
import ui.component.layout.group.OwleRow
import ui.component.layout.position.FillWidth
import ui.component.text.OwleText
import ui.theme.OwleTheme

@Composable
fun OElementStat(
    icon: Icons,
    text: String
) {
    OwleRow {
        FillWidth(20) { Icon(icon = icon) }
        FillWidth(80) {
            OwleText(
                text = text,
                textSize = OwleTheme.Text.Size.LG,
                color = OwleTheme.Color.Surface.TextOn
            ) {
                style { whiteSpace("nowrap") }
            }
        }
    }
}

@Composable
fun OElementStatDouble(
    leftStat: Pair<Icons, String>,
    rightStat: Pair<Icons, String>
) {

    OwleRow(horizontalArrangement = Arrangement.SpaceEvenly) {
        OElementStat(leftStat.first, leftStat.second)
        OElementStat(rightStat.first, rightStat.second)
    }

}