package com.wini.owle.dto.workflows

import com.wini.owle.ksp.IntValueMin
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Workflow.Companion.MAX_NAME_LENGTH
import kotlinx.serialization.Serializable

/**
 * Represents a phase within a workflow, grouping steps or other phases.
 * Phases support hierarchical organization, enabling subphases.
 */
@OwleModel
@Serializable
data class Phase(

    @LongValueMin(0)
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
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    /**
     * Description of the phase, describing its purpose or grouping intent.
     */
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val description: String?,

    /**
     * Specifies the order in which the phase appears within the workflow.
     */
    @IntValueMin(0)
    val displayOrder: Int
) : StandardDto