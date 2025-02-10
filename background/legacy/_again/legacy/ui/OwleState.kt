package ui

import com.wini.owle.MockModels
import com.wini.owle.models.WorkflowFolder
import com.wini.owle.models.WorkflowSummary

data class OwleState(
    val folderRoot: WorkflowFolder = MockModels.workflowFolderHierarchy(),
    val summaries: List<WorkflowSummary> = MockModels.workflowSummaries()
)