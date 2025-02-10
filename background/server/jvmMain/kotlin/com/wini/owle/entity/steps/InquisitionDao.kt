package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.Inquisitions
import com.wini.owle.entity.workflows.StepDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a conditional check or "inquisition" within a workflow,
 * analyzing a specific data point value and determining if workflow redirection is required.
 */
class InquisitionDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<InquisitionDao>(Inquisitions)

    /**
     * The workflow step that includes this inquisition as a conditional check.
     */
    var workflowStep by StepDao referencedOn Inquisitions.step

    /**
     * Specifies the class of inquisition (e.g., condition type or criteria).
     */
    var className by Inquisitions.clazz

    /**
     * The data point whose value is analyzed by this inquisition to determine workflow direction.
     */
    var dataPoint by DataPointDao referencedOn Inquisitions.dataPoint

    /**
     * Specifies the workflow step to execute if the condition is met in this inquisition.
     */
    var onMetWorkflowStep by StepDao referencedOn Inquisitions.onMetStep

    /**
     * Determines the order in which this inquisition is executed within its associated workflow step.
     */
    var executionOrder by Inquisitions.executionOrder
}