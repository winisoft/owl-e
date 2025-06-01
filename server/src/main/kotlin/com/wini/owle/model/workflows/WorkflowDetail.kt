package com.wini.owle.model.workflows

import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Workflow


data class WorkflowDetail(
    private val workflowHierarchy: WorkflowHierarchyNode,
    private val dependencies: List<Package>,
    private val dataPoints: List<DataPoint>,

)
