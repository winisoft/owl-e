package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * The step that follows this one when no inquisition or junction has redirected.
 *
 * Keyed on the source step, so absence of a row is what makes a step terminal — there is no
 * "next step" column on `steps` to leave null.
 */
class StepDefaultRouteEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<StepDefaultRouteEntity>(StepDefaultRoutes)

    /**
     * The step being routed from — same row id as this entity.
     */
    val step: StepEntity get() = StepEntity[id]

    /**
     * The step to execute next.
     */
    var nextStep by StepEntity referencedOn StepDefaultRoutes.nextStep

    /**
     * The workflow both steps belong to.
     */
    var workflow by WorkflowEntity referencedOn StepDefaultRoutes.workflow
}
