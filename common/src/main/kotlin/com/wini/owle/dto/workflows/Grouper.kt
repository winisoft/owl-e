package com.wini.owle.dto.workflows

import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable
import com.wini.owle.schema.error.ConstraintDoubleRange
import com.wini.owle.error.DoubleValueOoB

/**
 * Represents a grouping within a workflow, a wrapper around members that are steps or other Groupers
 * in themselves. No barrier of access is implied by their boundary, nor do the elements grouped together
 * coordinate in any extraordinary way - this organizational convenience is wholly to make a workflow more intuitively
 * understood.
 */
@ModelDefinition
@ApiEndpointDefinition("api.workflows.grouper", ["workflows"], "/grouper")
@Serializable
data class Grouper(

        override val id: ULong = 0UL,

    /**
     * Workflow containing this grouper.
     */
    val workflow: Workflow,

    /**
     * Reference to the parent grouper, if this grouper is found within a hierarchical structure
     * of these, or null if this Grouper sits at the 'root scope' of a Workflow.
     */
    val parentGrouper: Grouper? = null,

    /**
     * Name of the grouper, used for identification within the workflow.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_255)
    val name: String,

    /**
     * Description of the grouper, describing its purpose or grouping intent.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_1024)
    val description: String?,

    @ConstraintDoubleRange(DoubleValueOoB::class, Schema.Canvas.MIN, Schema.Canvas.MAX)
    val posX: Double,

    @ConstraintDoubleRange(DoubleValueOoB::class, Schema.Canvas.MIN, Schema.Canvas.MAX)
    val posY: Double,

) : ULongDto