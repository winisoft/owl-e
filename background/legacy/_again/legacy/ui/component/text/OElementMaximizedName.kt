package ui.component.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.pc
import org.jetbrains.compose.web.css.width
import ui.theme.OwleTheme


@Composable
fun OElementMaximizedName(name: String) {
    OwleText(
        name,
        OwleTheme.Text.Size.XL2,
        OwleTheme.Color.Surface.TextOn,
        OwleTheme.Text.Style.BOLD
    ) {
        style {
            width(100.pc)
        }
    }
}