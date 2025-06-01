package com.wini.owle.model.workflows

import com.wini.owle.dto.workflows.Phase
import com.wini.owle.dto.workflows.Step

data class PhaseHierarchyNode(
    private val phase: Phase,
    private val childPhases: List<PhaseHierarchyNode>,
    private val childSteps: List<Step>
)