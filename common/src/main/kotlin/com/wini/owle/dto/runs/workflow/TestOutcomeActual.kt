package com.wini.owle.dto.runs.workflow

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.TimestampMustBeChronologicallyCoherent
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.TimestampFormatMustMatchIso8601
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Stores the outcome of a test case execution, indicating whether the test passed or failed.
 * Includes the execution timestamp for audit and debugging purposes.
 */
@ModelDefinition
@ApiEndpointDefinition("api.runs.workflow.workflowTestResult", ["runs"], "/workflow-test")
@Serializable
data class TestOutcomeActual(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    val dataPoint: DataPoint? = null,

    /**
     * Boolean indicating whether the test passed (true) or failed (false).
     */
    val passed: Boolean,


) : ULongDto