package com.wini.owle.dto.workflows

import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Represents a phase within a workflow, grouping steps or other phases.
 * Phases support hierarchical organization, enabling subphases.
 */
@ModelDefinition
@Serializable
data class Phase(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong = 0UL,

    /**
     * Workflow containing this phase.
     */
    val workflow: Workflow,

    /**
     * Reference to the parent phase, if this phase is part of a hierarchical structure.
     */
    val parentPhase: Phase? = null,

    /**
     * Name of the phase, used for identification within the workflow.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_255)
    val name: String,

    /**
     * Description of the phase, describing its purpose or grouping intent.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_1024)
    val description: String?,

    /**
     * Specifies the order in which the phase appears within the workflow.
     */
    @ConstraintSimple([PositiveValueIntError::class])
    val displayOrder: Int
) : ULongDto