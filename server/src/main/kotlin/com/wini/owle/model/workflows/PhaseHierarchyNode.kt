package com.wini.owle.model.workflows

import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.dto.workflows.Step

data class PhaseHierarchyNode(
    private val grouper: Grouper,
    private val childPhases: List<PhaseHierarchyNode>,
    private val childSteps: List<Step>
)