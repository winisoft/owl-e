package com.wini.owle.entity.workflows

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a step within a workflow, defining its tool dependency, next and previous steps,
 * and whether it serves as the workflow's head step.
 */
class StepEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<StepEntity>(Steps)

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

    var nextStep by StepEntity optionalReferencedOn Steps
}