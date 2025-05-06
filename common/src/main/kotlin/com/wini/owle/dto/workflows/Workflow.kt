package com.wini.owle.dto.workflows

import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.schema.TimestampCoherent
import com.wini.owle.schema.TimestampIso8601Format
import com.wini.owle.schema.UpdatedBeforeCreatedTimestamp
import com.wini.owle.dto.StandardDto
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