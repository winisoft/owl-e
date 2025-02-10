package com.wini.owle.entity.steps

import com.wini.owle.entity.WorkflowJunctions
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.WorkflowDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a junction connecting one workflow to another, allowing a transition to a target workflow.
 * A junction may include conditions under which the transition is triggered,
 * and prerequisite injections required for the target workflow.
 */
class WorkflowJunctionDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<WorkflowJunctionDao>(WorkflowJunctions)

    /**
     * Step in the source workflow from which this transition originates.
     */
    var sourceStep by StepDao referencedOn WorkflowJunctions.sourceStep

    /**
     * Workflow that this junction transitions to.
     */
    var targetWorkflow by WorkflowDao referencedOn WorkflowJunctions.targetWorkflow

    /**
     * Inquisition condition required to activate this junction, if specified.
     */
    var triggerInquisition by InquisitionDao optionalReferencedOn WorkflowJunctions.triggerInquisition
}