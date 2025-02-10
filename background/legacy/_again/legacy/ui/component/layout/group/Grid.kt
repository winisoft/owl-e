package ui.component.layout.group

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import ui.component.style.CssProperty

/**
 * Supports an alternative to Compose foundation's GridLayout
 */
@Composable
fun Grid(
    gap: CSSNumeric = 8.px,
    columnCount: Int,
    fractionalWidths: String,
    content: @Composable () -> Unit
) {
    Div(attrs = {
        style {
            CssProperty.Display.Grid(this)
            CssProperty.Gap.Both(gap)(this)
            CssProperty.GridTemplateColumns.Repeat("repeat($columnCount, $fractionalWidths)")(this)
        }
    }) {
        content()
    }
}

@Composable
fun QuarterGrid(content: @Composable () -> Unit) {

    Div(attrs = {
        classes("quarter-grid")
    }) {
        content()
    }

}