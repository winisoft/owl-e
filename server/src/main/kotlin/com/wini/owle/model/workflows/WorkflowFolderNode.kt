package com.wini.owle.model.workflows

import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder


data class WorkflowFolderNode(
    val folder: WorkflowFolder,
    val childFolders: List<WorkflowFolderNode>,
    val workflows: List<Workflow>
)
