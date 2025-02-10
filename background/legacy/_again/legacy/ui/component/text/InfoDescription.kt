package ui.component.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import ui.component.style.Padding
import ui.theme.OwleTheme


@Composable
fun InfoDescription(text: String? = null) {

    if (text.isNullOrEmpty().not())
        Padding(6.px, 4.px, 2.px, 2.px) {
            OwleText(
                text = text!!,
                textSize = OwleTheme.Text.Size.LG,
                color = OwleTheme.Color.Info
            )

        }
}