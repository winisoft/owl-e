package preview.component.oelement.workflow

import androidx.compose.runtime.Composable
import preview.PreviewItems
import preview.item.PreviewItemWithBottomLabel
import com.wini.owle.models.WorkflowSummary as WorkflowSummaryModel
import ui.component.oelement.workflow.WorkflowSummary


@Composable
fun PreviewWorkflowSummaries(isExpandedInitial: Boolean = false) {

    PreviewItems(
        label = "Workflow Summaries",
        isExpandedInitial = isExpandedInitial,
        columnCount = 2,
        fractionalWidths = "1fr 1fr",
        items = listOf(
            { PreviewItemWithBottomLabel(
                block = {
                    WorkflowSummary(
                        summary = WorkflowSummaryModel(
                            id = 0L,
                            name = "Generate 3-D Image",
                            description = "Create a 3D object model in Blender using an image and a depth map",
                            createdAt = "",
                            updatedAt = "",
                            stepsCount = 123,
                            phasesCount = 20,
                            dataPointsCount = 45,
                            junctionsCount = 0
                        ),
                        actions = {}
                    )
                },
                name = "With Phases"
            )
            },
            { PreviewItemWithBottomLabel(
                block = {
                    WorkflowSummary(
                        summary = WorkflowSummaryModel(
                            id = 0L,
                            name = "Generate 3-D Image",
                            description = "Create a 3D object model in Blender using an image and a depth map",
                            createdAt = "",
                            updatedAt = "",
                            stepsCount = 123,
                            phasesCount = 0,
                            dataPointsCount = 45,
                            junctionsCount = 0
                        ),
                        actions = {}
                    )
                },
                name = "Without Phases"
            )
            },
            { PreviewItemWithBottomLabel(
                block = {
                    WorkflowSummary(
                        summary = WorkflowSummaryModel(
                            id = 0L,
                            name = "Generate 3-D Image",
                            description = "Create a 3D object model in Blender using an image and a depth map",
                            createdAt = "",
                            updatedAt = "",
                            stepsCount = 123,
                            phasesCount = 20,
                            dataPointsCount = 45,
                            junctionsCount = 5
                        ),
                        actions = {}
                    )
                },
                name = "With Junctions"
            )
            },
            { PreviewItemWithBottomLabel(
                block = {
                    WorkflowSummary(
                        summary = WorkflowSummaryModel(
                            id = 0L,
                            name = "Generate 3-D Image",
                            description = "",
                            createdAt = "",
                            updatedAt = "",
                            stepsCount = 123,
                            phasesCount = 20,
                            dataPointsCount = 45,
                            junctionsCount = 0
                        ),
                        actions = {}
                    )
                },
                name = "Without Description"
            )
            }
        ),

    )



}