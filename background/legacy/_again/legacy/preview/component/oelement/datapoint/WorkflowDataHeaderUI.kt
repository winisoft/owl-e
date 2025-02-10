package preview.component.oelement.datapoint

import androidx.compose.runtime.Composable
import preview.PreviewItems
import preview.item.PreviewItemWithBottomLabel
import ui.component.oelement.datapoint.WorkflowDataHeaderUI


@Composable
fun PreviewWorkflowDataHeaderUI(isExpandedInitial: Boolean = false) {

    PreviewItems(
        label = "Workflow Data Header",
        isExpandedInitial = isExpandedInitial,
        columnCount = 1,
        fractionalWidths = "1fr",
        items = listOf(
            { PreviewItemWithBottomLabel(
                block = {
                    WorkflowDataHeaderUI(
                        dataPointsCountTotal = 3,
                        onExpandList = {},
                        onSearchClick = {},
                    )
                },
                name = "With 3 Present"
            )}
        )
    )


}