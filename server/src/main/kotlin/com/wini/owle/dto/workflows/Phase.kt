package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents a phase within a workflow, grouping steps or other phases.
 * Phases support hierarchical organization, enabling subphases.
 */
@Serializable
data class Phase(
    override val id: Long = 0L,
    /**
     * Workflow containing this phase.
     */
    val workflow: Workflow,
    /**
     * Reference to the parent phase, if this phase is part of a hierarchical structure.
     */
    val parentPhase: Phase?,
    /**
     * Name of the phase, used for identification within the workflow.
     */
    val name: String,
    /**
     * Description of the phase, describing its purpose or grouping intent.
     */
    val description: String?,
    /**
     * Specifies the order in which the phase appears within the workflow.
     */
    val displayOrder: Int
) : StandardDto