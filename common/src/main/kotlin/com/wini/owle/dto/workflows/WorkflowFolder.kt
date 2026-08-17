package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.TimestampMustBeChronologicallyCoherent
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.TimestampFormatMustMatchIso8601
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 * Supports nested folders and categorizes workflows.
 */
@ModelDefinition
@ApiEndpointDefinition("api.workflows.workflowFolder", ["workflows"], "/workflow-folder")
@Serializable
data class WorkflowFolder(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,
    
    /**
     * Reference to the parent folder, enabling hierarchical folder structure.
     */
    val parentFolder: WorkflowFolder? = null,
    
    /**
     * Name of the folder, required and unique for organizing workflows.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.WorkflowFolder.NAME_MAX_LENGTH)
    val name: String,

    /**
     * Optional description of the folder's purpose or content.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.WorkflowFolder.DESC_MAX_LENGTH)
    val description: String? = null,
    
    /**
     * Timestamp for when the folder was created.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val createdAt: String,
    
    /**
     * Timestamp for when the folder was last updated.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val updatedAt: String
) : ULongDto