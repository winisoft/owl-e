package ui.component.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontStyle
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLParagraphElement
import ui.theme.OwleTheme


@Composable
fun OwleText(
    text: String,
    textSize: OwleTheme.Text.Size = OwleTheme.Text.Size.BASE,
    color: OwleTheme.ColorValue = OwleTheme.Color.Surface.TextOn,
    style: OwleTheme.Text.Style? = null,
    attrs: (AttrsScope<HTMLParagraphElement>.() -> Unit)? = null
) {

    P({
        style {
            color(color.value)
            fontSize(textSize.rem)
            style?.apply { fontStyle(value) }
        }
        attrs?.invoke(this)
    }) {
        Text(text)
    }

}