package com.wini.owle.mapper.environs.pkg

import com.wini.owle.dto.environs.pkg.Distribution
import com.wini.owle.dto.environs.pkg.DistributionPackage
import com.wini.owle.entity.environs.pkg.DistributionPackageEntity
import com.wini.owle.mapper.DbMapper
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.environs.version.VersionMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class DistributionPackageMapper(
    private val daoDelegate: DaoDelegate<CompositeID, DistributionPackage, DistributionPackageEntity>,
    private val versionMapper: VersionMapper,
    private val requirementMapper: PackageDistRequirementMapper,
    private val distributionMapper: DistributionMapper
): DbMapper<CompositeID, DistributionPackage, DistributionPackageEntity> {


    override fun toEntity(dto: DistributionPackage): DistributionPackageEntity {
        return daoDelegate.findOrCreate(dto) {
            distribution = distributionMapper.toEntity(dto.distribution)
            version = versionMapper.toEntity(dto.version)
            versionString = dto.versionString
            summary = dto.summary
            keywords = dto.keywords
            description = dto.description
            descriptionContentType = dto.descriptionContentType
            license = dto.license
            homePage = dto.homePage
            projectUrl = dto.projectUrl
            downloadUrl = dto.downloadUrl
            docsUrl = dto.docsUrl
            yanked = dto.yanked
            lastSerial = dto.lastSerial
        }
    }

    override fun toDto(entity: DistributionPackageEntity): DistributionPackage {
        return DistributionPackage(
            distribution = Distribution(entity.distribution.id.value),
            version = versionMapper.toDto(entity.version),
            //requirements = entity.requirements.map { requirementMapper.toDto(it) },
            versionString = entity.versionString,
            summary = entity.summary,
            keywords = entity.keywords,
            description = entity.description,
            descriptionContentType = entity.descriptionContentType,
            license = entity.license,
            homePage = entity.homePage,
            projectUrl = entity.projectUrl,
            downloadUrl = entity.downloadUrl,
            docsUrl = entity.docsUrl,
            yanked = entity.yanked,
            lastSerial = entity.lastSerial
        )
    }
}