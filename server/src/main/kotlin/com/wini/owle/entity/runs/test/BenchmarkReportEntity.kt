package com.wini.owle.entity.runs.test

import com.wini.owle.entity.runs.BenchmarkReports
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * Timing summary for a workflow execution.
 */
class BenchmarkReportEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<BenchmarkReportEntity>(BenchmarkReports)

    /**
     * Relationship to the associated workflow execution.
     */
    var workflowExecution by WorkflowRunEntity referencedOn BenchmarkReports.workflowExecution

    /**
     * Total execution time for the workflow, in seconds to millisecond precision.
     */
    var executionTime by BenchmarkReports.executionTime
}
