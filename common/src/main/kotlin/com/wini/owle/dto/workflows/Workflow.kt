package com.wini.owle.dto.workflows

import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.ksp.TimestampCoherent
import com.wini.owle.ksp.TimestampIso8601Format
import com.wini.owle.ksp.UpdatedBeforeCreatedTimestamp
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