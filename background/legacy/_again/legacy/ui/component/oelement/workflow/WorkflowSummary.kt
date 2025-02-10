package ui.component.oelement.workflow

import androidx.compose.runtime.Composable
import com.wini.owle.models.WorkflowSummary
import ui.action.WorkflowSummaryActions
import ui.action.WorkflowSummaryActions.*
import ui.component.button.IconButton
import ui.component.img.Icons
import ui.component.layout.panel.SurfaceCard
import ui.component.oelement.OElementHeader
import ui.component.row.OElementStatDouble
import ui.component.text.HeaderText
import ui.component.text.InfoDescription


@Composable
fun WorkflowSummary(
    summary: WorkflowSummary,
    actions: (WorkflowSummaryActions) -> Unit
) {

    SurfaceCard {

        OElementHeader(
            icon = Icons.Workflow,
            text = { HeaderText(summary.name) },
            controlPanel = {
                IconButton(icon = Icons.ExpandFullscreen) { actions(ExpandFullScreen(summary.id)) }
                IconButton(icon = Icons.Copy) { actions(Copy(summary.id)) }
                IconButton(icon = Icons.Clone) { actions(Clone(summary.id)) }
                IconButton(icon = Icons.Trash) { actions(Delete(summary.id)) }
            }
        )

        InfoDescription(summary.description)

        OElementStatDouble(
            leftStat = Icons.Steps to summary.stepsCountText,
            rightStat = Icons.Phases to summary.phasesCountText
        )

        OElementStatDouble(
            leftStat = Icons.DataItem to summary.dataPointsText,
            rightStat = Icons.Junction to summary.junctionsText
        )
    }
}
