package com.wini.owle.entity.steps

import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Persists a step's result into a data point. Runs unconditionally on normal return — writes are
 * never suppressed, because an inquisition can only inspect a value that already has a home.
 */
class DataPointWriteEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<DataPointWriteEntity>(DataPointWrites)

    /**
     * The data point written to.
     */
    var dataPoint by DataPointEntity referencedOn DataPointWrites.dataPoint

    /**
     * The step responsible for this write.
     */
    var step by StepEntity referencedOn DataPointWrites.step

    /**
     * Write-site transform applied to the value on the way out.
     */
    var transformOccurrence by TransformOccurrenceEntity optionalReferencedOn DataPointWrites.transformOccurrence

    /**
     * Additional data points fed to the write-site transform, in position order.
     */
    val args: SizedIterable<DataPointWriteArgEntity>
        get() = DataPointWriteArgEntity
            .find { DataPointWriteArgs.dataPointWrite eq id }
            .orderBy(DataPointWriteArgs.position to SortOrder.ASC)
}
