package com.wini.owle.entity.workflows

import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.entity.steps.DataPointWrites
import com.wini.owle.entity.steps.DataPointReadEntity
import com.wini.owle.entity.steps.DataPointReads
import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * A typed slot holding a value for the life of a run.
 *
 * Data points are the only thing steps read from and write to, and the only thing an inquisition
 * can inspect — which is why a result must be given a home here before it can be reasoned about.
 */
class DataPointEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<DataPointEntity>(DataPoints)

    /**
     * Workflow containing this data point.
     */
    var workflow by WorkflowEntity referencedOn DataPoints.workflow

    /**
     * Specifies the data type for the data point, linking to a type declaration.
     */
    var typeNode by TypeNodeEntity referencedOn DataPoints.typeNode

    /**
     * Name of the data point, used to identify it within the workflow.
     */
    var name by DataPoints.name

    /**
     * Explains the meaning of the data point to users.
     */
    var description by DataPoints.description

    /**
     * Marks a data point that must be supplied as a run input rather than produced by a step.
     */
    var isPrerequisite by DataPoints.isPrerequisite

    /**
     * Preset value, used for initialization or defaults.
     */
    var presetValue by DataPoints.presetValue

    /**
     * Specifies how the data point's value is stored: as text, blob, or file.
     */
    var storeMethod by DataPoints.storeMethod

    /**
     * Timestamp for when the dataPoint was created.
     */
    var createdAt by DataPoints.createdAt

    /**
     * Timestamp for when the dataPoint was last updated.
     */
    var updatedAt by DataPoints.updatedAt

    // --- access sites ---------------------------------------------------------------------

    /**
     * Every step argument this data point supplies.
     */
    val readsFrom: SizedIterable<DataPointReadEntity>
            by DataPointReadEntity referrersOn DataPointReads.dataPoint

    /**
     * Every step write that targets this data point.
     */
    val writesTo: SizedIterable<DataPointWriteEntity> by DataPointWriteEntity referrersOn DataPointWrites.dataPoint
}
