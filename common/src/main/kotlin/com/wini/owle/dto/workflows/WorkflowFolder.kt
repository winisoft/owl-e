package com.wini.owle.dto.workflows

import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.schema.TimestampCoherent
import com.wini.owle.schema.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Workflow.Companion.MAX_NAME_LENGTH
import kotlinx.serialization.Serializable

/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 * Supports nested folders and categorizes workflows.
 */
@OwleModel
@Serializable
data class WorkflowFolder(

    @LongValueMin(0)
    override val id: Long,
    
    /**
     * Reference to the parent folder, enabling hierarchical folder structure.
     */
    val parentFolder: WorkflowFolder? = null,
    
    /**
     * Name of the folder, required and unique for organizing workflows.
     */
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,
    
    /**
     * Optional description of the folder's purpose or content.
     */
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMin(1)
    @StringLengthMax(MAX_NAME_LENGTH)
    val description: String? = null,
    
    /**
     * An icon to distinguish the folder for the user
     */
    val img: ByteArray? = null,
    
    /**
     * Timestamp for when the folder was created.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val createdAt: String,
    
    /**
     * Timestamp for when the folder was last updated.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val updatedAt: String
) : StandardDto