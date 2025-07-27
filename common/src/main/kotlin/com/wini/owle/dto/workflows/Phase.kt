package com.wini.owle.dto.workflows

import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Workflow.Companion.MAX_NAME_LENGTH
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import kotlinx.serialization.Serializable

/**
 * Represents a phase within a workflow, grouping steps or other phases.
 * Phases support hierarchical organization, enabling subphases.
 */
@ModelDefinition
@Serializable
data class Phase(

    @ConstraintSimple([PositiveValueLongError::class])
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
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val name: String,

    /**
     * Description of the phase, describing its purpose or grouping intent.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_DESC_LENGTH)
    val description: String?,

    /**
     * Specifies the order in which the phase appears within the workflow.
     */
    @ConstraintSimple([PositiveValueIntError::class])
    val displayOrder: Int
) : StandardDto