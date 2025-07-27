package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintDoubleRange
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.error.DoubleValueRangeError
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueLongError
import kotlinx.serialization.Serializable

/**
 * Collects overall benchmarking data for a specific workflow execution,
 * including execution time, step counts, and the distribution of AI and predetermined steps.
 */
@ModelDefinition
@Serializable
data class BenchmarkReport(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Relationship to the associated workflow execution.
     */
    val workflowExecution: WorkflowExecution,

    /**
     * Total execution time for the workflow, measured in seconds.
     */
    @ConstraintDoubleRange(DoubleValueRangeError::class, EXE_TIME_MIN, EXE_TIME_MAX)
    val totalExecutionTime: Double,

    /**
     * Number of steps dynamically determined by AI during execution.
     */
    @ConstraintSimple([PositiveValueIntError::class])
    val aiDeterminedSteps: Int,

    /**
     * Number of steps predetermined and set in the workflow configuration.
     */
    @ConstraintSimple([PositiveValueIntError::class])
    val predeterminedSteps: Int

) : StandardDto {

    companion object {
        const val EXE_TIME_MIN = 0.0
        const val EXE_TIME_MAX = 9_999_999.999
    }
}