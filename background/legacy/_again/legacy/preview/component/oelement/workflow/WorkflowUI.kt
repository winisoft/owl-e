package preview.component.oelement.workflow

import androidx.compose.runtime.Composable
import com.wini.owle.models.Workflow
import com.wini.owle.models.metadata.UserDesignedMetadata
import preview.PreviewItems
import ui.component.oelement.workflow.Workflow


@Composable
fun PreviewWorkflowUI(isExpandedInitial: Boolean = false) {

    PreviewItems(
        label = "Workflow",
        isExpandedInitial = isExpandedInitial,
        columnCount = 1,
        fractionalWidths = "1fr",
        items = listOf({
            Workflow(
                workflow = Workflow(
                    id = 0L,
                    name = "Convert Video From 2-D to 3-D",
                    description = "Use the DepthAnything AI model to generate a depth map and calculate a stereo image of each frame from a 2-D video file and generate a new 3-D video from it",
                    pythonVersion = "3.10.11",
                    parentFolder = null,
                    dataPoints = listOf(),
                    steps = emptyList(),
                    phases = emptyList(),
                    metadata = UserDesignedMetadata.mock,
                    createdAt = "",
                    updatedAt = ""
                )
            )
        })
    )

}