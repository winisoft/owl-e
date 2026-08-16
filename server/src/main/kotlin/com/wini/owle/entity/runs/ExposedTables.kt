package com.wini.owle.entity.runs

import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.dto.runs.enums.ExecutionEventKind
import com.wini.owle.dto.runs.enums.ExecutionLogLevel
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.entity.steps.Inquisitions
import com.wini.owle.entity.user.Users
import com.wini.owle.entity.workflows.DataPoints
import com.wini.owle.entity.workflows.Multiflows
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp


object WorkflowExecutions : ULongIdTable("workflow_executions") {
    val workflow = reference("workflow_id", Workflows)

    /** Set when the run was entered as part of a Multiflow; members share one container. */
    val multiflow = reference("multiflow_id", Multiflows).nullable()
    val startedAt = timestamp("started_at").defaultExpression(CurrentTimestamp).nullable()
    val completedAt = timestamp("completed_at").nullable()
    val status = enumerationByName("status", 20, ExecutionStatus::class).default(ExecutionStatus.RUNNING)
}

object BenchmarkReports : ULongIdTable("benchmark_reports") {
    val workflowExecution = reference("workflow_run_id", WorkflowExecutions)
    val executionTime = decimal("execution_time", 10, 3)
}

object ExecutionLogs : ULongIdTable("execution_logs") {
    val workflowExecution = reference("workflow_execution_id", WorkflowExecutions, onDelete = ReferenceOption.CASCADE)
    val seq = ulong("seq")
    val loggedAt = timestamp("logged_at").defaultExpression(CurrentTimestamp)
    val eventKind = enumerationByName("event_kind", 32, ExecutionEventKind::class)
    val logLevel = enumerationByName("log_level", 8, ExecutionLogLevel::class).default(ExecutionLogLevel.INFO)
    val workflow = reference("workflow_id", Workflows).nullable()
    val step = reference("step_id", Steps).nullable()
    val inquisition = reference("inquisition_id", Inquisitions).nullable()
    val dataPoint = reference("data_point_id", DataPoints).nullable()

    /** Free-form per-event payload; stored as JSON text rather than a typed column. */
    val detail = text("detail").nullable()
    val message = text("message").nullable()

    init {
        uniqueIndex("uq_execution_seq", workflowExecution, seq)
    }
}


object TestCases : ULongIdTable("test_cases") {
    val user = reference("user_id", Users)
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val workflow = reference("workflow_id", Workflows).nullable()
    val step = reference("step_id", Steps).nullable()
}

object TestExpectedOutcomes : ULongIdTable("test_expected_outcomes") {
    val testCase = reference("test_case_id", TestCases)
    val dataPoint = reference("data_point_id", DataPoints)
    val expectedValue = text("expected_value").nullable()
    val operator = enumerationByName("operator", 16, ComparisonOperator::class)
}

object TestInputs : ULongIdTable("test_inputs") {
    val testCase = reference("test_case_id", TestCases)
    val dataPoint = reference("data_point_id", DataPoints)
    val value = text("value").nullable()
}

object TestResults : ULongIdTable("test_results") {
    val testCase = reference("test_case_id", TestCases)
    val workflowExecution = reference("workflowExecutionId", WorkflowExecutions)
    val passed = bool("passed")
    val executedAt = timestamp("executedAt").defaultExpression(CurrentTimestamp).nullable()
}

object StepTestRuns : ULongIdTable("test_step_run") {
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val started = timestamp("started").nullable()
    val completed = timestamp("completed").nullable()
    val status = enumerationByName("status", 20, ExecutionStatus::class).default(ExecutionStatus.READY)
}
