package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.ProvisionDataPointIdFactory
import com.wini.owle.entity.steps.ProvisionDataPointEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID

class ProvisionDataPointMapper(
    private val daoFactory: DaoFactory<ProvisionDataPointEntity, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<ProvisionDataPoint> = ProvisionDataPointIdFactory
): DbMapper<ProvisionDataPoint, ProvisionDataPointEntity>() {

    override fun toEntity(dto: ProvisionDataPoint): ProvisionDataPointEntity {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            ProvisionDataPointEntity.findById(id)
        } ?: ProvisionDataPointEntity.create(
            provisionId = dto.provisionId,
            dataPointId = dto.dataPointId
        )
    }

    override fun toDto(entity: ProvisionDataPointEntity): ProvisionDataPoint {
        return ProvisionDataPoint(
            provisionId = entity.provision.id.value,
            dataPointId = entity.dataPointEntity.id.value
        )
    }

}