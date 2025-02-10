package ui.component.text

import androidx.compose.runtime.Composable
import ui.theme.OwleTheme


@Composable
fun HeaderText(
    text: String
) {
    OwleText(
        text = text,
        textSize = OwleTheme.Text.Size.XL,
        color = OwleTheme.Color.Surface.TextOn,
        style = OwleTheme.Text.Style.BOLD
    )
}