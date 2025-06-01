package com.wini.owle.entity.runs.test

import com.wini.owle.entity.runs.BenchmarkReports
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Collects overall benchmarking data for a specific workflow execution,
 * including execution time, step counts, and the distribution of AI and predetermined steps.
 */
class BenchmarkReportEntity(id: EntityID<ULong>) : ULongEntity(id) {
    companion object : ULongEntityClass<BenchmarkReportEntity>(BenchmarkReports)

    /**
     * Relationship to the associated workflow execution.
     */
    var workflowExecution by WorkflowRunEntity referencedOn BenchmarkReports.workflowExecution

    /**
     * Total execution time for the workflow, measured in seconds.
     */
    var totalExecutionTime by BenchmarkReports.totalExecutionTime

}