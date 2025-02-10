package preview.component.button.toggle

import androidx.compose.runtime.Composable
import preview.PreviewItems
import preview.item.PreviewItemWithBottomLabel
import ui.component.button.toggle.CollapseExpandToggleBtn
import ui.component.button.toggle.FilterToggleBtn


@Composable
fun PreviewToggleIconButtons(isExpandedInitial: Boolean = false) {
    PreviewItems(
        label = "Toggle Icon Buttons",
        isExpandedInitial = isExpandedInitial,
        columnCount = 1,
        fractionalWidths = "1fr",
        items = listOf(
            {
                PreviewItemWithBottomLabel(
                    block = { CollapseExpandToggleBtn({}, isExpandedInitial = isExpandedInitial) },
                    name = "Collapse / Expand"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = { FilterToggleBtn({}) },
                    name = "Filter On / Off"
                )
            },
        )
    )


}