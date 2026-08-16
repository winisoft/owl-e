package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * The junction an inquisition triggers when it evaluates true, transitioning to another workflow.
 *
 * Keyed on the inquisition itself. [step] is carried alongside so composite FKs can enforce that
 * the inquisition and the junction hang off the same step.
 */
class InquisitionJunctionRedirectEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<InquisitionJunctionRedirectEntity>(InquisitionJunctionRedirects)

    /**
     * The inquisition being redirected — same row id as this entity.
     */
    val inquisition: InquisitionEntity get() = InquisitionEntity[id]

    /**
     * The junction to take.
     */
    var junction by WorkflowJunctionEntity referencedOn InquisitionJunctionRedirects.junction

    /**
     * The step both the inquisition and the junction belong to.
     */
    var step by StepEntity referencedOn InquisitionJunctionRedirects.step
}
