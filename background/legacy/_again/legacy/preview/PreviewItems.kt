package preview

import androidx.compose.runtime.Composable
import preview.group.PreviewGroup
import ui.component.layout.group.Grid


@Composable
fun PreviewItems(
    label: String,
    isExpandedInitial: Boolean = false,
    items: List<@Composable () -> Unit>,
    columnCount: Int,
    fractionalWidths: String
) {

    PreviewGroup(
        label = label,
        isExpandedInitial = isExpandedInitial
    ) {

        Grid(columnCount = columnCount, fractionalWidths = fractionalWidths) {
            items.forEach { item ->
                item()
            }
        }

    }
}
