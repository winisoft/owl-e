package com.wini.owle.entity.workflows

import com.wini.owle.entity.Phases
import com.wini.owle.entity.Steps
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a step within a workflow, defining its tool dependency, next and previous steps,
 * and whether it serves as the workflow's head step.
 */
class StepEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StepEntity>(Steps)

    /**
     * Workflow that contains this step.
     */
    var workflow by WorkflowEntity referencedOn Steps.workflow

    /**
     * Name of the step, used for identification within the workflow.
     */
    var name by Steps.name

    /**
     * Optional description of the step’s purpose or action.
     */
    var description by Steps.description

    /**
     * Tool associated with this step for execution.
     */
    var tool by ToolEntity referencedOn Steps.tool

    /**
     * Reference to the next step in the workflow sequence.
     */
    var defaultNext by StepEntity optionalReferencedOn Steps.defaultNext

    /**
     * Indicates if this step is the first step in the workflow.
     */
    var isWorkflowHead by Steps.isWorkflowHead

    /**
     * The order this step is presented among other steps and phases sitting at the same level of the hierarchy
     */
    var displayOrder by Steps.displayOrder

    /**
     * The phase within which this step is displayed. If null, it sits at the workflow level
     */
    var parentPhase by PhaseEntity optionalReferencedOn Phases

}