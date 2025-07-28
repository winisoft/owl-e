package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.schema.error.TimestampCoherent
import com.wini.owle.schema.error.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.schema.error.PreventsTimeParadoxes
import com.wini.owle.schema.error.StringNotEmpty
import kotlinx.serialization.Serializable

/**
 * Represents a workflow, containing steps and data points, and supports organization
 * into folders. Metadata is stored as JSON to allow for flexible information capture.
 */
@OwleModel
@UpdatedBeforeCreatedTimestamp("createdAt", "updatedAt")
@Serializable
data class Workflow(

    @LongValueMin(0)
    override val id: Long,
    
    /**
     * Name of the workflow, used for identification in the UI.
     */
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,
    
    /**
     * Description of the workflow's purpose or function.
     */
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val description: String?,
    
    /**
     * Specifies the Python version required to execute the workflow.
     * Defaults to the system's default if not provided.
     */
    val pythonVersion: String,
    
    /**
     * References the folder in which this workflow is stored.
     */
    val parentFolder: WorkflowFolder?,
    
    /**
     * Timestamp for when the workflow was created.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val createdAt: String,
    
    /**
     * Timestamp for when the workflow was last updated.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val updatedAt: String
) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
    }
}