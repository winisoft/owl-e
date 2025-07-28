package com.wini.owle.dto.workflows

import com.wini.owle.schema.IntValueMin
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Workflow.Companion.MAX_NAME_LENGTH
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.PositiveValueInt
import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringNotEmpty
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