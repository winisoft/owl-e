package preview.component.button

import androidx.compose.runtime.Composable
import preview.PreviewItems
import preview.item.PreviewItemWithBottomLabel
import ui.component.button.*


@Composable
fun PreviewIconButtons(isExpandedInitial: Boolean = false) {
    PreviewItems(
        label = "Standard Icon Buttons",
        columnCount = 1,
        fractionalWidths = "1fr",
        items = listOf(
            {
                PreviewItemWithBottomLabel(
                    block = {
                        AddItemIconButton(onClick = {})
                    },
                    "Add Item (Enabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        AddItemIconButton(onClick = {}, isEnabled = false)
                    },
                    "Add Item (Disabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        EditItemIconButton(onClick = {})
                    },
                    "Edit Item (Enabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        EditItemIconButton(onClick = {}, isEnabled = false)
                    },
                    "Edit Item (Disabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        ConfirmIconButton(onClick = {})
                    },
                    "Confirm (Enabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        ConfirmIconButton(onClick = {}, isEnabled = false)
                    },
                    "Confirm (Disabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        CancelIconButton(onClick = {})
                    },
                    "Cancel (Enabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        CancelIconButton(onClick = {}, isEnabled = false)
                    },
                    "Cancel (Disabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        DataTypeIconButton(onClick = {})
                    },
                    "Data Type (Enabled)"
                )
            },
            {
                PreviewItemWithBottomLabel(
                    block = {
                        DataTypeIconButton(onClick = {}, isEnabled = false)
                    },
                    "Data Type (Disabled)"
                )
            },
        )
    )
}