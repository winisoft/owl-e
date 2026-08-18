package com.wini.owle.entity.steps

import com.wini.owle.entity.typing.CallableSignatureEntity
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A routing predicate attached to a step's completion.
 *
 * Like a tool, an inquisition carries its own Python source in [body] and a typed [signature];
 * its arguments are bound to data points through [dataPointReads]. Evaluated after the step's
 * writes land, in [executionOrder]. The first one to evaluate true routes — either to another
 * step ([stepRedirect]) or across a junction ([junctionRedirect]).
 */
class InquisitionEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<InquisitionEntity>(Inquisitions)

    /**
     * The workflow step whose completion this inquisition evaluates.
     */
    var step by StepEntity referencedOn Inquisitions.step

    /**
     * Author-facing note. Carries no execution meaning.
     */
    var note by Inquisitions.note

    /**
     * Determines the order in which this inquisition is evaluated within its associated step.
     */
    var executionOrder by Inquisitions.executionOrder

    /**
     * Typed call contract for [body], when one has been resolved.
     */
    var signature by CallableSignatureEntity optionalReferencedOn Inquisitions.signature

    /**
     * The Python predicate source.
     */
    var body by Inquisitions.body

    /**
     * Data points bound to this predicate's parameters.
     */
    val dataPointReads: SizedIterable<DataPointReadEntity>
            by DataPointReadEntity optionalReferrersOn DataPointReads.inquisition

    /**
     * Where this inquisition routes within the same workflow, if that is its outcome.
     */
    val stepRedirect: InquisitionStepRedirectEntity?
        get() = InquisitionStepRedirectEntity.findById(id)

    /**
     * The junction this inquisition triggers, if it transitions workflow instead.
     */
    val junctionRedirect: InquisitionJunctionRedirectEntity?
        get() = InquisitionJunctionRedirectEntity.findById(id)
}
