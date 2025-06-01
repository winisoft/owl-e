package com.wini.owle.entity.runs

import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.dto.runs.enums.TestAssertion
import com.wini.owle.entity.user.Users
import com.wini.owle.entity.workflows.DataPoints
import com.wini.owle.entity.workflows.Steps
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp


object BenchmarkReports : ULongIdTable("benchmark_reports") {
    val workflowExecution = reference("workflow_run_id", WorkflowExecutions, onDelete = ReferenceOption.CASCADE)
    val totalExecutionTime = decimal("execution_time", 10, 3)
}

object ExecutionLogs : ULongIdTable("execution_logs") {
    val stepExecution = reference("stepExecutionId", StepTestRuns, onDelete = ReferenceOption.CASCADE)
    val timestamp = timestamp("timestamp").defaultExpression(CurrentTimestamp)
    val logLevel = enumerationByName("logLevel", 10, LogLevel::class)
    val message = text("message")
}

object WorkflowExecutions : ULongIdTable("workflow_executions") {
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE)
    val startedAt = timestamp("started_at").defaultExpression(CurrentTimestamp)
    val completedAt = timestamp("completed_at").nullable()
    val status = enumerationByName("status", 20, ExecutionStatus::class).default(ExecutionStatus.RUNNING)
}

object TestResults : ULongIdTable("test_results") {
    val testCase = reference("test_case_id", TestCases, onDelete = ReferenceOption.CASCADE)
    val workflowExecution = reference("workflow_execution_id", WorkflowExecutions, onDelete = ReferenceOption.CASCADE)
    val passed = bool("passed")
    val executedAt = timestamp("executedAt").defaultExpression(CurrentTimestamp)
}

object TestInputs : ULongIdTable("test_inputs") {
    val testCase = reference("test_case_id", TestCases, onDelete = ReferenceOption.CASCADE)
    val dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val value = text("value").nullable()
    val generationMethod = enumerationByName("generationMethod", 20, GenerationMethod::class)
}

object TestCases : ULongIdTable("test_cases") {
    val user = reference("user_id", Users, onDelete = ReferenceOption.CASCADE).nullable()
    val name = varchar("name", 255)
    val description = varchar("description", 8192).nullable()
    val workflow = reference("workflow_id", Workflows, onDelete = ReferenceOption.CASCADE).nullable()
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE).nullable()
}

object StepTestRuns : ULongIdTable("test_step_run") {
    val step = reference("step_id", Steps, onDelete = ReferenceOption.CASCADE)
    val started = timestamp("started").nullable().default(null)
    val completed = timestamp("completed").nullable().default(null)
    val status = enumerationByName("status", 20, ExecutionStatus::class).default(ExecutionStatus.RUNNING)
}

object TestOutcomeExpectations : ULongIdTable("test_outcome_expectation") {
    var dataPoint = reference("data_point_id", DataPoints, onDelete = ReferenceOption.CASCADE)
    val assertion = enumerationByName("assertion", 20, TestAssertion::class)
}