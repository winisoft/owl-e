package com.wini.owle.mapper.environs

import com.wini.owle.dto.environs.VersionOverflow
import com.wini.owle.entity.environs.OverflowVersionComponentEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.enums.OverflowComponentTypeMapper

class OverflowVersionComponentMapper(
    private val daoFactory: DaoFactory<OverflowVersionComponentEntity, Long>,
    private val pythonVersionMapper: PythonVersionMapper,
    private val componentTypeMapper: OverflowComponentTypeMapper
): DbMapper<VersionOverflow, OverflowVersionComponentEntity>() {

    override fun toEntity(dto: VersionOverflow): OverflowVersionComponentEntity {
        val applyProps: OverflowVersionComponentEntity.() -> Unit = {
            pythonVersion = pythonVersionMapper.toEntity(dto.pythonVersion)
            componentType = componentTypeMapper.toEntity(dto.componentType)
            stringValue = dto.stringValue
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else OverflowVersionComponentEntity.findById(dto.id)
            ?.apply(applyProps)
            ?: throw IllegalStateException("Entity with id=${dto.id} not found")
    }

    override fun toDto(entity: OverflowVersionComponentEntity): VersionOverflow {
        return VersionOverflow(
            id = entity.id.value,
            pythonVersion = pythonVersionMapper.toDto(entity.pythonVersion),
            componentType = componentTypeMapper.toDto(entity.componentType),
            stringValue = entity.stringValue
        )
    }
}