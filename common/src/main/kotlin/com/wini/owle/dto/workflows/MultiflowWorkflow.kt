package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable
import com.wini.owle.schema.error.ConstraintDoubleRange
import com.wini.owle.error.DoubleValueOoB
import com.wini.owle.schema.Schema
import com.wini.owle.error.HexColorFormat
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax

/**
 * Membership of a workflow in a Multiflow, with its placement on that canvas.
 */
@ModelDefinition
@Serializable
data class MultiflowWorkflow(

    val multiflow: Multiflow,

    val workflow: Workflow,

    @ConstraintDoubleRange(DoubleValueOoB::class, Schema.Canvas.MIN, Schema.Canvas.MAX)
    val posX: Double,

    @ConstraintDoubleRange(DoubleValueOoB::class, Schema.Canvas.MIN, Schema.Canvas.MAX)
    val posY: Double,

    @ConstraintSimple([HexColorFormat::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.MultiflowWorkflow.COLOR_LENGTH)
    val color: String?,

    val isCollapsed: Boolean,

) : CompositeDto {

    override val owleId: String get() = "multiflow=${multiflow.owleId},workflow=${workflow.owleId}"
}
