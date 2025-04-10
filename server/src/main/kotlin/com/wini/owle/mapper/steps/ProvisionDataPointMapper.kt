package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.ProvisionDataPointIdFactory
import com.wini.owle.entity.steps.ProvisionDataPointDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID

class ProvisionDataPointMapper(
    private val daoFactory: DaoFactory<ProvisionDataPointDao, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<ProvisionDataPointDto> = ProvisionDataPointIdFactory
): DbMapper<ProvisionDataPointDto, ProvisionDataPointDao>() {

    override fun toEntity(dto: ProvisionDataPointDto): ProvisionDataPointDao {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            ProvisionDataPointDao.findById(id)
        } ?: ProvisionDataPointDao.create(
            provisionId = dto.provisionId,
            dataPointId = dto.dataPointId
        )
    }

    override fun toDto(entity: ProvisionDataPointDao): ProvisionDataPointDto {
        return ProvisionDataPointDto(
            provisionId = entity.provision.id.value,
            dataPointId = entity.dataPoint.id.value
        )
    }

}