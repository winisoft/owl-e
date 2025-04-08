package com.wini.owle.entity.composteid

import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.ProvisionDataPoints
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column


object ProvisionDataPointIdFactory: CompositeIdFactoryImpl<ProvisionDataPointDto>() {

    override fun fromDtoInstance(dto: ProvisionDataPointDto): CompositeID {
        return CompositeID {
            it[ProvisionDataPoints.provision] = dto.provisionId
            it[ProvisionDataPoints.dataPoint] = dto.dataPointId
        }
    }

    override val fkReferences: Map<Column<EntityID<Long>>, String> = buildMap {
        ProvisionDataPoints.provision to "provisionId"
        ProvisionDataPoints.dataPoint to "dataPointId"
    }
}