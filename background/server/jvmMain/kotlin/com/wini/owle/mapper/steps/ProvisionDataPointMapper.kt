package com.wini.owle.mapper.steps

import com.wini.owle.dto.ProvisionDataPointDto
import com.wini.owle.entity.steps.ProvisionDataPointDao
import com.wini.owle.mapper.DbMapper


class ProvisionDataPointMapper: DbMapper<ProvisionDataPointDto, ProvisionDataPointDao>() {

    override fun ProvisionDataPointDto.toEntity(): ProvisionDataPointDao {
        return ProvisionDataPointDao.create(provisionId = provisionId, dataPointId = dataPointId)
    }

    override fun ProvisionDataPointDao.toDto(): ProvisionDataPointDto {
        return ProvisionDataPointDto(
            provisionId = provision.id.value,
            dataPointId = dataPoint.id.value
        )
    }

    override val applyProps: ProvisionDataPointDao.(ProvisionDataPointDto) -> Unit = {}
}