package com.wini.owle.entity.runs.workflow

import com.wini.owle.entity.TestResults
import com.wini.owle.entity.runs.test.TestCaseEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Stores the outcome of a test case execution, indicating whether the test passed or failed.
 * Includes the execution timestamp for audit and debugging purposes.
 */
class WorkflowTestResultEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<WorkflowTestResultEntity>(TestResults)

    /**
     * Relationship to the associated test case.
     */
    var testCaseEntity by TestCaseEntity referencedOn TestResults.testCase

    /**
     * Relationship to the associated workflow execution.
     */
    var workflowExecution by WorkflowRunEntity referencedOn TestResults.workflowExecution

    /**
     * Boolean indicating whether the test passed (true) or failed (false).
     */
    var passed by TestResults.passed

    /**
     * Timestamp indicating when the test was executed.
     */
    var executedAt by TestResults.executedAt
}