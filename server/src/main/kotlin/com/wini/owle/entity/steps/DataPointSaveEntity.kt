package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.Revisions
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a revision made to a data point following the successful execution of a step.
 * Tracks the affected data point, the responsible step, and optionally, any transformation.
 */
class DataPointSaveEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<DataPointSaveEntity>(Revisions)

    /**
     * The data point affected by this revision.
     */
    var dataPointEntity by DataPointEntity referencedOn Revisions.dataPoint

    /**
     * The step responsible for this revision.
     */
    var step by StepEntity referencedOn Revisions.step

}