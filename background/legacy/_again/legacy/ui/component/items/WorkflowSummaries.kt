package ui.component.items

import androidx.compose.runtime.Composable
import ui.component.oelement.workflow.WorkflowSummary
import com.wini.owle.models.WorkflowSummary as WorkflowSummaryModel
import ui.action.WorkflowSummaryActions
import ui.component.layout.group.Grid

@Composable
fun WorkflowSummariesGrid(summaries: List<WorkflowSummaryModel>, action: (WorkflowSummaryActions) -> Unit) {
    Grid(columnCount = 3, fractionalWidths = "1fr") {
        summaries.forEach {
            WorkflowSummary(it, action)
        }
    }
}