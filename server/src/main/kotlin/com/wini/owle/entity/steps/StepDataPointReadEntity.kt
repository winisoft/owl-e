package com.wini.owle.entity.steps

import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.typing.CallableParameterEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Supplies one parameter of a step's tool from a data point, optionally through a transform.
 *
 * Keyed on (step, callable parameter) — one binding per parameter per step, which is what makes
 * argument resolution total: every parameter either has a row here or the step cannot run.
 */
class StepDataPointReadEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<StepDataPointReadEntity>(StepDataPointReads)

    /**
     * The step doing the reading.
     */
    var step by StepEntity referencedOn StepDataPointReads.step

    /**
     * The tool parameter being supplied.
     */
    var callableParameter by CallableParameterEntity referencedOn StepDataPointReads.callableParameter

    /**
     * The data point supplying it.
     */
    var dataPoint by DataPointEntity referencedOn StepDataPointReads.dataPoint

    /**
     * Read-site transform applied to the value on the way in.
     */
    var transformOccurrence by TransformOccurrenceEntity optionalReferencedOn StepDataPointReads.transformOccurrence
}
