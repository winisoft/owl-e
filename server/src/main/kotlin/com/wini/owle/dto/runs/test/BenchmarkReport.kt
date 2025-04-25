package com.wini.owle.dto.runs.test

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import kotlinx.serialization.Serializable

/**
 * Collects overall benchmarking data for a specific workflow execution,
 * including execution time, step counts, and the distribution of AI and predetermined steps.
 */
@Serializable
data class BenchmarkReport(
    override val id: Long,
    /**
     * Relationship to the associated workflow execution.
     */
    val workflowExecution: WorkflowExecution,
    /**
     * Total execution time for the workflow, measured in seconds.
     */
    val totalExecutionTime: Double,
    /**
     * Total number of steps executed within the workflow.
     */
    val totalSteps: Int,
    /**
     * Number of steps dynamically determined by AI during execution.
     */
    val aiDeterminedSteps: Int,
    /**
     * Number of steps predetermined and set in the workflow configuration.
     */
    val predeterminedSteps: Int
) : StandardDto