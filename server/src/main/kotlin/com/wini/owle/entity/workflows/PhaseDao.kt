package com.wini.owle.entity.workflows

import com.wini.owle.entity.Phases
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a phase within a workflow, grouping steps or other phases.
 * Phases support hierarchical organization, enabling subphases.
 */
class PhaseDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PhaseDao>(Phases)

    /**
     * Workflow containing this phase.
     */
    var workflow by WorkflowDao referencedOn Phases.workflow

    /**
     * Reference to the parent phase, if this phase is part of a hierarchical structure.
     */
    var parentPhase by PhaseDao optionalReferencedOn Phases.parentPhase

    /**
     * Name of the phase, used for identification within the workflow.
     */
    var name by Phases.name

    /**
     * Description of the phase, describing its purpose or grouping intent.
     */
    var description by Phases.description

    /**
     * Specifies the order in which the phase appears within the workflow.
     */
    var displayOrder by Phases.displayOrder
}