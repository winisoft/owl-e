package com.wini.owle.dto.workflows

import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.dto.workflows.Workflow.Companion.MAX_NAME_LENGTH
import com.wini.owle.dto.workflows.enums.StoreMethod
import kotlinx.serialization.Serializable

/**
 * Represents a data point within a workflow, used for tracking values,
 * inputs, or outputs that may be accessed by workflow steps.
 */
@OwleModel
@Serializable
data class DataPoint(

    @LongValueMin(0)
    override val id: Long,
    /**
     * Workflow containing this data point.
     */
    val workflow: Workflow,
    /**
     * Name of the data point, used to identify it within the workflow.
     */
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    /**
     * Explains the meaning of the DataPoint to users
     */
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val description: String? = null,

    /**
     * Preset value for the data point, which may be used for initialization or defaults.
     */
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val presetValue: String? = null,
    /**
     * Specifies how the data point's value is stored: as text, blob, or file.
     */
    val storeMethod: StoreMethod,
    /**
     * Specifies the data type for the data point, linking to a type declaration.
     */
    val typeDeclaration: TypeDeclaration,
    /**
     * Timestamp for when the dataPoint was created.
     */
    val createdAt: String,
    /**
     * Timestamp for when the dataPoint was last updated.
     */
    val updatedAt: String
) : StandardDto