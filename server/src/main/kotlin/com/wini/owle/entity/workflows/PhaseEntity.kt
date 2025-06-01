package com.wini.owle.entity.workflows

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a phase within a workflow, grouping steps or other phases.
 * Phases support hierarchical organization, enabling subphases.
 */
class PhaseEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<PhaseEntity>(Phases)

    /**
     * Workflow containing this phase.
     */
    var workflow by WorkflowEntity referencedOn Phases.workflow

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

    val parentPhases get() = PhaseRelationEntity.find { PhaseRelations.child eq id }.map { it.parent }

    val childPhases get() = PhaseRelationEntity.find { PhaseRelations.parent eq id }.map { it.child }
}