package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Connects a step to another workflow.
 *
 * A junction is unconditional unless an inquisition claims it via an
 * [InquisitionJunctionRedirectEntity]; [triggeredBy] resolves that claim.
 */
class WorkflowJunctionEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<WorkflowJunctionEntity>(WorkflowJunctions)

    /**
     * Step in the source workflow from which this transition originates.
     */
    var sourceStep by StepEntity referencedOn WorkflowJunctions.sourceStep

    /**
     * Workflow that this junction transitions to.
     */
    var targetWorkflow by WorkflowEntity referencedOn WorkflowJunctions.targetWorkflow

    /**
     * Values lifted from the source workflow into the target's data points.
     */
    val injections: SizedIterable<DataPointInjectionEntity>
            by DataPointInjectionEntity referrersOn DataPointInjections.junction

    /**
     * The inquisition that gates this junction, if any; absent means unconditional.
     */
    val triggeredBy: InquisitionJunctionRedirectEntity?
        get() = InquisitionJunctionRedirectEntity
            .find { InquisitionJunctionRedirects.junction eq id }
            .firstOrNull()
}
