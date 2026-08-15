package com.wini.owle.dto.workflows

import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Represents a data point within a workflow, used for tracking values,
 * inputs, or outputs that may be accessed by workflow steps.
 */
@ModelDefinition
@Serializable
data class DataPoint(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,
    /**
     * Workflow containing this data point.
     */
    val workflow: Workflow,
    /**
     * Name of the data point, used to identify it within the workflow.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_127)
    val name: String,

    /**
     * Explains the meaning of the DataPoint to users
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_1024)
    val description: String? = null,

    /**
     * Preset value for the data point, which may be used for initialization or defaults.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_16000)
    val presetValue: String? = null,
    /**
     * Specifies how the data point's value is stored: as text, blob, or file.
     */
    val storeMethod: StoreMethod,
    /**
     * Specifies the data type for the data point, linking to a type declaration.
     */
    val typeDeclaration: TypeNode,
    /**
     * Timestamp for when the dataPoint was created.
     */
    val createdAt: String,
    /**
     * Timestamp for when the dataPoint was last updated.
     */
    val updatedAt: String
) : ULongDto