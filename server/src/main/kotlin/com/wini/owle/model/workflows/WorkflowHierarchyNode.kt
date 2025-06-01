package com.wini.owle.model.workflows

import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow


data class WorkflowHierarchyNode(
    private val workflow: Workflow,
    private val childPhases: List<PhaseHierarchyNode>,
    private val childSteps: List<Step>
)
