package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeNodeMapper


class DataPointMapper(
    private val daoDelegate: DaoDelegate<ULong, DataPoint, DataPointEntity>,
    private val typeDeclarationMapper: TypeNodeMapper,
    private val workflowMapper: WorkflowMapper
): DbMapper<ULong, DataPoint, DataPointEntity> {

    override fun toEntity(dto: DataPoint): DataPointEntity {
        return daoDelegate.findOrCreate(dto) {
            workflow = workflowMapper.toEntity(dto.workflow)
            name = dto.name
            presetValue = dto.presetValue
            storeMethod = dto.storeMethod
            typeNode = typeDeclarationMapper.toEntity(dto.typeVarNode)
            description = dto.description
        }
    }

    override fun toDto(entity: DataPointEntity): DataPoint {
        return DataPoint(
            id = entity.id.value,
            workflow = workflowMapper.toDto(entity.workflow),
            name = entity.name,
            presetValue = entity.presetValue,
            storeMethod = entity.storeMethod,
            typeDeclaration = typeDeclarationMapper.toDto(entity.typeNode),
            description = entity.description,
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
        )
    }
}