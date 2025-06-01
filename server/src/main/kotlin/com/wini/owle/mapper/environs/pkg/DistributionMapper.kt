package com.wini.owle.mapper.environs.pkg

import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.environs.pkg.DistributionEntity
import com.wini.owle.entity.environs.pkg.Distributions
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class DistributionMapper(
    val daoDelegate: DaoDelegate<String, Distribution, DistributionEntity>,
): DbMapper<String, Distribution, DistributionEntity> {

    override fun toEntity(dto: Distribution): DistributionEntity {
        val applyProps: DistributionEntity.() -> Unit  = {
            name = EntityID(dto.name, Distributions)
        }

        return daoDelegate.findOrCreate(dto, applyProps)
    }

    override fun toDto(entity: DistributionEntity): Distribution {
        return Distribution(
            name = entity.id.value
        )
    }
}