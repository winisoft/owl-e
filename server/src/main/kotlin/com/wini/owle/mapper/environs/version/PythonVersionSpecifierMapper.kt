package com.wini.owle.mapper.environs.version

import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.dto.environs.version.PythonVersionSpecifier
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.environs.version.PythonVersionSpecifierEntity
import com.wini.owle.mapper.DbMapper


class PythonVersionSpecifierMapper(
    private val daoDelegate: DaoDelegate<ULong, PythonVersionSpecifier, PythonVersionSpecifierEntity>,
    private val pythonVersionMapper: PythonVersionMapper
): DbMapper<ULong, PythonVersionSpecifier, PythonVersionSpecifierEntity> {

    override fun toEntity(dto: PythonVersionSpecifier): PythonVersionSpecifierEntity {
        return daoDelegate.findOrCreate(dto) {
            version = pythonVersionMapper.toEntity(dto.pythonVersion)
        }
    }

    override fun toDto(entity: PythonVersionSpecifierEntity): PythonVersionSpecifier {
        return PythonVersionSpecifier(
            id = entity.id.value,
            pythonVersion = pythonVersionMapper.toDto(entity.version),
            operator = VersionOperator.EXACT, // entity.operator,
            wildcard = WildcardSegment.MAJOR //entity.wildcard
        )
    }
}