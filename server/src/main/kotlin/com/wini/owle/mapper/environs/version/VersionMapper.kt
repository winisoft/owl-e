package com.wini.owle.mapper.environs.version

import com.wini.owle.dto.environs.version.Version
import com.wini.owle.entity.environs.version.VersionEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper


class VersionMapper(
    private val daoDelegate: DaoDelegate<ULong, Version, VersionEntity>,
    private val overflowVersionsMapper: VersionOverflowMapper
): DbMapper<ULong, Version, VersionEntity> {

    override fun toEntity(dto: Version): VersionEntity {
        return daoDelegate.findOrCreate(dto) {
            epoch = dto.epoch
            major = dto.major
            minor = dto.minor
            micro = dto.micro
            phase = dto.phase
            phaseNumber = dto.phaseNumber
            postNumber = dto.postNumber
            devNumber = dto.devNumber
            local = dto.local
            hasOverflow = dto.hasOverflow
        }

    }

    override fun toDto(entity: VersionEntity): Version {
        return Version(
            id = entity.id.value,
            epoch = entity.epoch,
            major = entity.major,
            minor = entity.minor,
            micro = entity.micro,
            phase = entity.phase,
            phaseNumber = entity.phaseNumber,
            postNumber = entity.postNumber,
            devNumber = entity.devNumber,
            local = entity.local,
            hasOverflow = entity.hasOverflow,
            overflowComponents = entity.versionOverflows.map { overflowVersionsMapper.toDto(it) }
        )
    }
}