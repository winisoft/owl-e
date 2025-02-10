package ui.component.layout.position

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div


@Composable
fun Spacer(width: CSSNumeric = 0.px, height: CSSNumeric = 0.px) {
    Div(attrs = {
        style {
            property("width", width)
            property("height", height)
        }
    })
}
