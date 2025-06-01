package com.wini.owle.mapper.environs.pkg

import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.environs.pkg.PackageDistRequirementEntity
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.version.VersionMapper


class PackageDistRequirementMapper(
    private val daoDelegate: DaoDelegate<ULong, PackageDistRequirement, PackageDistRequirementEntity>,
    private val distributionMapper: DistributionMapper,
    private val versionMapper: VersionMapper
): DbMapper<ULong, PackageDistRequirement, PackageDistRequirementEntity> {

    override fun toEntity(dto: PackageDistRequirement): PackageDistRequirementEntity {
        return daoDelegate.findOrCreate(dto) {
            dependentDistribution = distributionMapper.toEntity(dto.dependentDistribution)
            dependentVersion = versionMapper.toEntity(dto.dependentVersion)
            requiredDistribution = distributionMapper.toEntity(dto.requiredDistribution)
            requiredVersion = dto.requiredVersion?.let { versionMapper.toEntity(it) }
            operator = dto.operator
            wildcardSegment = dto.wildcardSegment
        }
    }

    override fun toDto(entity: PackageDistRequirementEntity): PackageDistRequirement {
        return PackageDistRequirement(
            id = entity.id.value,
            dependentDistribution = distributionMapper.toDto(entity.requiredDistribution),
            dependentVersion = versionMapper.toDto(entity.dependentVersion),
            requiredDistribution = distributionMapper.toDto(entity.requiredDistribution),
            requiredVersion = entity.requiredVersion?.run { versionMapper.toDto(this) },
            operator = entity.operator,
            wildcardSegment = entity.wildcardSegment
        )
    }
}