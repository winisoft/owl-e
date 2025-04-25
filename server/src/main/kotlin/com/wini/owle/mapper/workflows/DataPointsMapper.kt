package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.workflows.enums.StoreMethodMapper


class DataPointsMapper(
    private val daoFactory: DaoFactory<DataPointEntity, Long>,
    private val typeDeclarationMapper: TypeDeclarationsMapper,
    private val workflowMapper: WorkflowsMapper,
    private val storeMethodMapper: StoreMethodMapper
) : DbMapper<DataPoint, DataPointEntity>() {

    override fun toEntity(dto: DataPoint): DataPointEntity {
        val applyProps: DataPointEntity.() -> Unit = {
            workflow = workflowMapper.toEntity(dto.workflow)
            name = dto.name
            presetValue = dto.presetValue
            storeMethod = storeMethodMapper.toEntity(dto.storeMethod)
            typeDeclarationEntity = typeDeclarationMapper.toEntity(dto.typeDeclaration)
            description = dto.description
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            DataPointEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("DataPoint with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: DataPointEntity): DataPoint {
        return DataPoint(
            id = entity.id.value,
            workflow = workflowMapper.toDto(entity.workflow),
            name = entity.name,
            presetValue = entity.presetValue,
            storeMethod = storeMethodMapper.toDto(entity.storeMethod),
            typeDeclaration = typeDeclarationMapper.toDto(entity.typeDeclarationEntity),
            description = entity.description,
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
        )
    }
}