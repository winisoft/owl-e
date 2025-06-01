package com.wini.owle.mapper.environs.version

import com.wini.owle.dto.environs.version.PythonVersion
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.environs.version.PythonVersionEntity
import com.wini.owle.mapper.DbMapper


class PythonVersionMapper(
    private val daoDelegate: DaoDelegate<ULong, PythonVersion, PythonVersionEntity>,
    private val versionMapper: VersionMapper
): DbMapper<ULong, PythonVersion, PythonVersionEntity> {

    override fun toEntity(dto: PythonVersion): PythonVersionEntity {

        return daoDelegate.findOrCreate(dto) {

        }
    }

    override fun toDto(entity: PythonVersionEntity): PythonVersion {
        return PythonVersion(
            id = entity.id.value,
            version = versionMapper.toDto(entity.version)
        )
    }
}