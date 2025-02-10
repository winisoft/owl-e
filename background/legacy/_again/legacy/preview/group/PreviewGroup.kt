package preview.group

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.Div
import preview.Previews


@Composable
fun PreviewGroup(
    label: String,
    isExpandedInitial: Boolean = false,
    content: @Composable () -> Unit
) {

    var isExpanded by remember { mutableStateOf(isExpandedInitial) }

    Div(
        attrs = {
            classes("flex", "flex-col") // Vertical spacing between label and grid
        }
    ) {

        PreviewGroupHeader(
            name = label,
            toggleExpanded = { isExpandedEventArgs: Boolean ->
                isExpanded = isExpandedEventArgs
            }
        )

        if (isExpanded) {
            content()
        }

    }

}