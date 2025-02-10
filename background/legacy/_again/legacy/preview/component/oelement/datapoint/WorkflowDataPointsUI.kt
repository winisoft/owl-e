package preview.component.oelement.datapoint

import androidx.compose.runtime.Composable
import com.wini.owle.dto.enums.StoreMethod
import com.wini.owle.models.*
import preview.PreviewItems
import preview.item.PreviewItemWithBottomLabel
import ui.component.oelement.datapoint.WorkflowDataPointsUI


@Composable
fun PreviewWorkflowDataPointsUI(isExpandedInitial: Boolean = false) {

    PreviewItems(
        label = "Workflow UI",
        isExpandedInitial = isExpandedInitial,
        columnCount = 1,
        fractionalWidths = "1fr",
        items = listOf(
            { PreviewItemWithBottomLabel(
                block = {
                    WorkflowDataPointsUI(
                        dataPoints = listOf(
                            DataPoint(
                                id = 0L,
                                workflowId = 1L,
                                name = "",
                                description = "",
                                presetValue = null,
                                storeMethod = StoreMethod.Text,
                                typeDeclaration = TypeDeclaration(
                                    id = 0L,
                                    typeDefinition = TypeDefinition(
                                        id = 0L,
                                        name = "",
                                        namespace = "",
                                        versionSpec = VersionSpec(
                                            id = 0L,
                                            null,
                                            null,
                                            null
                                        )
                                    ),
                                    pkg = Package(
                                        id = 0L,
                                        name = "",
                                        disambiguation = null,
                                        versionSpec = VersionSpec(
                                            id = 0L,
                                            null,
                                            null,
                                            null
                                        )
                                    )
                                ),
                                createdAt = "2024-12-20T15:00:00",
                                updatedAt = "2024-12-20T15:00:00"
                            )
                        )
                    )
                },
                name = ""
            )}
        )
    )
        
}