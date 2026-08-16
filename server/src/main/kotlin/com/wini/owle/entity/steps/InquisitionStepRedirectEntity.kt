package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * The step an inquisition routes to when it evaluates true, within the same workflow.
 *
 * Keyed on the inquisition itself — at most one step redirect per inquisition. [workflow] is
 * carried alongside [step] so a composite FK can enforce that the target is in the same workflow.
 */
class InquisitionStepRedirectEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<InquisitionStepRedirectEntity>(InquisitionStepRedirects)

    /**
     * The inquisition being redirected — same row id as this entity.
     */
    val inquisition: InquisitionEntity get() = InquisitionEntity[id]

    /**
     * The step to execute.
     */
    var step by StepEntity referencedOn InquisitionStepRedirects.step

    /**
     * The workflow that step must belong to.
     */
    var workflow by WorkflowEntity referencedOn InquisitionStepRedirects.workflow
}
