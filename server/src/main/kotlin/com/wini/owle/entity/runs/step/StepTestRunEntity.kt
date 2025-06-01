package com.wini.owle.entity.runs.step

import com.wini.owle.entity.runs.StepTestRuns
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents the execution instance of a step within a workflow execution.
 * Tracks the status, start and completion times, and links to execution logs.
 */
class StepTestRunEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<StepTestRunEntity>(StepTestRuns)

    /**
     * Relationship to the step associated with this execution.
     */
    var step by StepEntity referencedOn StepTestRuns.step

    /**
     * Timestamp for when the step execution started (which may not have happened yet, in which case, null)
     */
    var started by StepTestRuns.started

    /**
     * Timestamp for when the step execution completed, if applicable.
     */
    var completed by StepTestRuns.completed

    /**
     * Execution status of the step, indicating current state.
     */
    var status by StepTestRuns.status

}