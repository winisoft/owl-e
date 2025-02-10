package com.wini.owle.entity.workflows

import com.wini.owle.entity.DataPoints
import com.wini.owle.entity.ProvisionDataPoints
import com.wini.owle.entity.steps.ProvisionDao
import com.wini.owle.entity.typing.TypeDeclarationDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a data point within a workflow, used for tracking values,
 * inputs, or outputs that may be accessed by workflow steps.
 */
class DataPointDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<DataPointDao>(DataPoints)

    /**
     * Workflow containing this data point.
     */
    var workflow by WorkflowDao referencedOn DataPoints.workflow

    /**
     * Specifies the data type for the data point, linking to a type declaration.
     */
    var typeDeclaration by TypeDeclarationDao referencedOn DataPoints.typeDeclaration

    /**
     * Name of the data point, used to identify it within the workflow.
     */
    var name by DataPoints.name

    /**
     * Preset value for the data point, which may be used for initialization or defaults.
     */
    var presetValue by DataPoints.presetValue

    /**
     * Specifies how the data point's value is stored: as text, blob, or file.
     */
    var storeMethod by DataPoints.storeMethod

    /**
     * Explains the meaning of the DataPoint to users
     */
    var description by DataPoints.description

    /**
     * Provisions that utilize this DataPoint
     */
    var provisions by ProvisionDao via ProvisionDataPoints

    /**
     * Timestamp for when the dataPoint was created.
     */
    var createdAt by DataPoints.createdAt

    /**
     * Timestamp for when the dataPoint was last updated.
     */
    var updatedAt by DataPoints.updatedAt
}