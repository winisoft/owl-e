package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 * Supports nested folders and categorizes workflows.
 */
@ModelDefinition
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
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_127)
    val name: String,
    
    /**
     * Optional description of the folder's purpose or content.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_1024)
    val description: String? = null,
    
    /**
     * An icon to distinguish the folder for the user
     */
    val img: ByteArray? = null,
    
    /**
     * Timestamp for when the folder was created.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val createdAt: String,
    
    /**
     * Timestamp for when the folder was last updated.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val updatedAt: String
) : ULongDto