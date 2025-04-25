package com.wini.owle.dto.runs.workflow

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.test.TestCase
import kotlinx.serialization.Serializable

/**
 * Stores the outcome of a test case execution, indicating whether the test passed or failed.
 * Includes the execution timestamp for audit and debugging purposes.
 */
@Serializable
data class WorkflowTestResult(
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
    val executedAt: String
) : StandardDto