package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.ProvisionDataPoints
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class ProvisionDataPointEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<ProvisionDataPointEntity>(ProvisionDataPoints) {

        fun buildId(provisionId: Long, dataPointId: Long): CompositeID {
            return CompositeID {
                it[ProvisionDataPoints.provision] = provisionId
                it[ProvisionDataPoints.dataPoint] = dataPointId
            }
        }

        fun create(provisionId: Long, dataPointId: Long): ProvisionDataPointEntity {
            val compositeID = buildId(provisionId, dataPointId)
            return ProvisionDataPointEntity(EntityID(compositeID, ProvisionDataPoints))
        }
    }

    var provision by ToolParamDataPointEntity referencedOn ProvisionDataPoints.provision

    var dataPointEntity by DataPointEntity referencedOn ProvisionDataPoints.dataPoint

}