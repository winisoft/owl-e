package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.ProvisionDataPoints
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

class ProvisionDataPointDao(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<ProvisionDataPointDao>(ProvisionDataPoints) {

        fun buildId(provisionId: Long, dataPointId: Long): CompositeID {
            return CompositeID {
                it[ProvisionDataPoints.provision] = provisionId
                it[ProvisionDataPoints.dataPoint] = dataPointId
            }
        }

        fun create(provisionId: Long, dataPointId: Long): ProvisionDataPointDao {
            val compositeID = buildId(provisionId, dataPointId)
            return ProvisionDataPointDao(EntityID(compositeID, ProvisionDataPoints))
        }
    }

    var provision by ProvisionDao referencedOn ProvisionDataPoints.provision

    var dataPoint by DataPointDao referencedOn ProvisionDataPoints.dataPoint

}