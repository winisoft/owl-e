package com.wini.owle.dto.runs.test

import com.wini.owle.schema.DoubleMinAndMax
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import kotlinx.serialization.Serializable

/**
 * Collects overall benchmarking data for a specific workflow execution,
 * including execution time, step counts, and the distribution of AI and predetermined steps.
 */
@OwleModel
@Serializable
data class BenchmarkReport(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Relationship to the associated workflow execution.
     */
    val workflowExecution: WorkflowExecution,

    /**
     * Total execution time for the workflow, measured in seconds.
     */
    @DoubleMinAndMax(EXE_TIME_MIN, EXE_TIME_MAX)
    val totalExecutionTime: Double,

    /**
     * Number of steps dynamically determined by AI during execution.
     */
    val aiDeterminedSteps: Int,

    /**
     * Number of steps predetermined and set in the workflow configuration.
     */
    val predeterminedSteps: Int

) : StandardDto {

    companion object {
        const val EXE_TIME_MIN = 0.0
        const val EXE_TIME_MAX = 9_999_999.999
    }
}