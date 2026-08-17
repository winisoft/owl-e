package com.wini.owle.dto.runs.workflow

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * The pass/fail outcome of one test case against one run.
 */
@ModelDefinition
@Serializable
data class TestResult(

    override val id: ULong,

    val testCase: TestCase,

    val workflowExecution: WorkflowExecution,

    val passed: Boolean,

    val executedAt: String,

    ) : ULongDto
