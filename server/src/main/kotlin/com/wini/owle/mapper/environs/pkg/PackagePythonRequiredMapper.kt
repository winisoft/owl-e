package com.wini.owle.mapper.environs.pkg

import com.wini.owle.dto.environs.pkg.PackagePythonRequirement
import com.wini.owle.entity.environs.pkg.PackagePythonRequirementEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.version.VersionMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class PackagePythonRequiredMapper(
    private val daoDelegate: DaoDelegate<CompositeID, PackagePythonRequirement, PackagePythonRequirementEntity>,
    private val versionMapper: VersionMapper,
    private val distributionMapper: DistributionMapper
): DbMapper<CompositeID, PackagePythonRequirement, PackagePythonRequirementEntity> {

    override fun toEntity(dto: PackagePythonRequirement): PackagePythonRequirementEntity {
         return daoDelegate.findOrCreate(dto) {
            distribution = distributionMapper.toEntity(dto.distribution)
            version = versionMapper.toEntity(dto.packageVersion)
        }
    }

    override fun toDto(entity: PackagePythonRequirementEntity): PackagePythonRequirement {
        return PackagePythonRequirement(
            distribution = distributionMapper.toDto(entity.distribution),
            version = versionMapper.toDto(entity.version),
            wildcardSegment = entity.wildcard,
            operator = entity.operator
        )
    }
}