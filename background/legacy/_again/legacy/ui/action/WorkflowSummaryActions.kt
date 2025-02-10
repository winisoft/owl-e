package ui.action


sealed class WorkflowSummaryActions {

    data class ExpandFullScreen(val id: Long): WorkflowSummaryActions()

    data class Clone(val id: Long): WorkflowSummaryActions()

    data class Copy(val id: Long): WorkflowSummaryActions()

    data class Delete(val id: Long): WorkflowSummaryActions()

}