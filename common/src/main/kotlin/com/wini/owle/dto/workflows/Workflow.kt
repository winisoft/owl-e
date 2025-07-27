package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.error.TimestampFormatError
import kotlinx.serialization.Serializable

/**
 * Represents a workflow, containing steps and data points, and supports organization
 * into folders. Metadata is stored as JSON to allow for flexible information capture.
 */
@ModelDefinition
//@PreventsTimeParadoxes("createdAt", "updatedAt")
@Serializable
data class Workflow(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,
    
    /**
     * Name of the workflow, used for identification in the UI.
     */
    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val name: String,
    
    /**
     * Description of the workflow's purpose or function.
     */
    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
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
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val createdAt: String,
    
    /**
     * Timestamp for when the workflow was last updated.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val updatedAt: String
) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
    }
}