package com.wini.owle.entity.runs.workflow

import com.wini.owle.entity.runs.BenchmarkReports
import com.wini.owle.entity.runs.ExecutionLogs
import com.wini.owle.entity.runs.TestResults
import com.wini.owle.entity.runs.WorkflowExecutions
import com.wini.owle.entity.runs.step.ExecutionLogEntity
import com.wini.owle.entity.runs.test.BenchmarkReportEntity
import com.wini.owle.entity.workflows.MultiflowEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * One execution of a workflow. When the run was entered as part of a Multiflow, [multiflowId]
 * identifies the enclosing closure — every member workflow shares a single container, so runs
 * that share a multiflow id also shared an environment.
 */
class WorkflowRunEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<WorkflowRunEntity>(WorkflowExecutions)

    /**
     * The workflow being executed in this instance.
     */
    var workflow by WorkflowEntity referencedOn WorkflowExecutions.workflow

    /**
     * The enclosing Multiflow, when this run is part of one.
     */
    var multiflow by MultiflowEntity optionalReferencedOn WorkflowExecutions.multiflow

    /**
     * Date and time the workflow execution started.
     */
    var startedAt by WorkflowExecutions.startedAt

    /**
     * Date and time the workflow execution completed, if applicable.
     */
    var completedAt by WorkflowExecutions.completedAt

    /**
     * Current status of the workflow execution, e.g., running, completed, failed, or cancelled.
     */
    var status by WorkflowExecutions.status

    /**
     * The run's full audit trail, in emission order.
     */
    val logs: SizedIterable<ExecutionLogEntity>
        get() = ExecutionLogEntity
            .find { ExecutionLogs.workflowExecution eq id }
            .orderBy(ExecutionLogs.seq to SortOrder.ASC)

    /**
     * Test outcomes recorded against this run.
     */
    val testResults: SizedIterable<TestResultEntity> by TestResultEntity referrersOn TestResults.workflowExecution

    /**
     * Timing reports for this run.
     */
    val benchmarkReports: SizedIterable<BenchmarkReportEntity>
            by BenchmarkReportEntity referrersOn BenchmarkReports.workflowExecution
}
