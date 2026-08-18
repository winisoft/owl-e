package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


/**
 * Collects overall benchmarking data for a specific workflow execution,
 * including execution time, step counts, and the distribution of AI and predetermined steps.
 */
@ModelDefinition
@ApiEndpointDefinition("api.runs.test.benchmarkReport", ["runs"], "/benchmark-report")
@Serializable
data class BenchmarkReport(

    override val id: ULong,

    /**
     * Relationship to the associated workflow execution.
     */
    val workflowExecution: WorkflowExecution,


    ) : ULongDto