package ui.component.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.pc
import org.jetbrains.compose.web.css.width
import ui.theme.OwleTheme


@Composable
fun OElementQuarterName(name: String) {
    OwleText(
        name,
        OwleTheme.Text.Size.XL,
        OwleTheme.Color.Surface.TextOn,
        OwleTheme.Text.Style.BOLD
    ) {
        style {
            width(100.pc)
        }
    }
}