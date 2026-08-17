package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.error.TimestampMustBeChronologicallyCoherent
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.TimestampFormatMustMatchIso8601
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.PreventsTimeParadox
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a workflow, containing steps and data points, and supports organization
 * into folders. Metadata is stored as JSON to allow for flexible information capture.
 */
@ModelDefinition
@ApiEndpointDefinition("api.workflows.workflow", ["workflows"], "/workflow")
@PreventsTimeParadox("createdAt", "updatedAt")
@Serializable
data class Workflow(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * Name of the workflow, used for identification in the UI.
     */
    @ConstraintSimple([StringContainsRestrictedChars::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Workflow.NAME_MAX_LENGTH)
    val name: String,

    /**
     * Description of the workflow's purpose or function.
     */
    @ConstraintSimple([StringContainsRestrictedChars::class, StringNotBlankError::class, StringNotEmptyError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Workflow.DESC_MAX_LENGTH)
    val description: String?,

    /**
     * Specifies the Python version required to execute the workflow.
     * Defaults to the system's default if not provided.
     */
    val pythonVersion: PythonVersion,

    /**
     * References the folder in which this workflow is stored.
     */
    val parentFolder: WorkflowFolder?,

    /**
     * Timestamp for when the workflow was created.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val createdAt: String,

    /**
     * Timestamp for when the workflow was last updated.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val updatedAt: String
) : ULongDto