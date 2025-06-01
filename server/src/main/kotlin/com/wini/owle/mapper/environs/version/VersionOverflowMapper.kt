package com.wini.owle.mapper.environs.version

import com.wini.owle.dto.environs.version.VersionOverflow
import com.wini.owle.entity.environs.version.VersionOverflowEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.version.PythonVersionMapper

class VersionOverflowMapper(
    private val daoDelegate: DaoDelegate<ULong, VersionOverflow, VersionOverflowEntity>,
    private val versionMapper: VersionMapper
): DbMapper<ULong, VersionOverflow, VersionOverflowEntity> {

    override fun toEntity(dto: VersionOverflow): VersionOverflowEntity {
        return daoDelegate.findOrCreate(dto) {

        }
    }

    override fun toDto(entity: VersionOverflowEntity): VersionOverflow {
        return VersionOverflow(
            id = entity.id.value,
            version = versionMapper.toDto(entity.referenceVersion),
            violation = entity.violation,
            stringValue = entity.value
        )
    }
}