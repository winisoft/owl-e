package com.wini.owle.entity.runs.test

import com.wini.owle.entity.BenchmarkingData
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Collects overall benchmarking data for a specific workflow execution,
 * including execution time, step counts, and the distribution of AI and predetermined steps.
 */
class BenchmarkReportEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<BenchmarkReportEntity>(BenchmarkingData)

    /**
     * Relationship to the associated workflow execution.
     */
    var workflowExecution by WorkflowRunEntity referencedOn BenchmarkingData.workflowExecution

    /**
     * Total execution time for the workflow, measured in seconds.
     */
    var totalExecutionTime by BenchmarkingData.totalExecutionTime

    /**
     * Total number of steps executed within the workflow.
     */
    var totalSteps by BenchmarkingData.totalSteps

    /**
     * Number of steps dynamically determined by AI during execution.
     */
    var aiDeterminedSteps by BenchmarkingData.aiDeterminedSteps

    /**
     * Number of steps predetermined and set in the workflow configuration.
     */
    var predeterminedSteps by BenchmarkingData.predeterminedSteps
}