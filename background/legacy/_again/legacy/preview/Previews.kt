package preview

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import preview.component.button.PreviewIconButtons
import preview.component.button.toggle.PreviewToggleIconButtons
import preview.component.img.PreviewIcons
import preview.component.oelement.folder.PreviewWorkflowFolderUIs
import preview.component.oelement.workflow.PreviewWorkflowSummaries
import preview.component.oelement.datapoint.PreviewWorkflowDataHeaderUI
import preview.component.oelement.datapoint.PreviewWorkflowDataPointsUI
import preview.component.oelement.workflow.PreviewWorkflowUI


@Composable
fun Previews() {
    Div(
        attrs = {
            classes("flex", "flex-col", "space-y-24", "p-16") // Vertical spacing between label and grid
        }
    ) {
        PreviewWorkflowFolderUIs(true)
        PreviewWorkflowUI()
        PreviewWorkflowDataPointsUI()
        PreviewWorkflowDataHeaderUI()
        PreviewWorkflowSummaries()
        PreviewToggleIconButtons()
        PreviewIconButtons()
        PreviewIcons()
    }
}