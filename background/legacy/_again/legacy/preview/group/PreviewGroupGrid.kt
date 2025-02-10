package preview.group

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.gap
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div


@Composable
fun PreviewGroupGrid(itemsPerRow: Int, content: @Composable () -> Unit) {
    Div(
        attrs = {
            classes("grid")
            style {
                gridTemplateColumns("repeat($itemsPerRow, minmax(0, 1fr))") // Dynamic columns
                gap(16.px)
            }
        }
    ) {

        content()
    }
}