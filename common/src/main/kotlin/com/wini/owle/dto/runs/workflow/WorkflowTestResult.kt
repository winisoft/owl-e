package com.wini.owle.dto.runs.workflow

import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.TimestampCoherent
import com.wini.owle.schema.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.test.TestCase
import kotlinx.serialization.Serializable

/**
 * Stores the outcome of a test case execution, indicating whether the test passed or failed.
 * Includes the execution timestamp for audit and debugging purposes.
 */
@OwleModel
@Serializable
data class WorkflowTestResult(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Relationship to the associated test case.
     */
    val testCase: TestCase,

    /**
     * Relationship to the associated workflow execution.
     */
    val workflowExecution: WorkflowExecution,

    /**
     * Boolean indicating whether the test passed (true) or failed (false).
     */
    val passed: Boolean,

    /**
     * Timestamp indicating when the test was executed.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val executedAt: String

) : StandardDto