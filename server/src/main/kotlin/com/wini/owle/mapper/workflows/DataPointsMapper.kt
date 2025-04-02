package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.entity.workflows.StoreMethod
import com.wini.owle.entity.workflows.StoreMethod.Companion.toDto
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper


class DataPointsMapper(
    private val daoFactory: DaoFactory<DataPointDao, Long>,
    private val typeDeclarationMapper: TypeDeclarationsMapper,
    private val workflowMapper: WorkflowsMapper
) : DbMapper<DataPointDto, DataPointDao>() {

    override fun toEntity(dto: DataPointDto): DataPointDao {
        val applyProps: DataPointDao.() -> Unit = {
            workflow = workflowMapper.toEntity(dto.workflow)
            name = dto.name
            presetValue = dto.presetValue
            storeMethod = StoreMethod.fromDto(dto.storeMethod)
            typeDeclaration = typeDeclarationMapper.toEntity(dto.typeDeclaration)
            description = dto.description
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            DataPointDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("DataPoint with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: DataPointDao): DataPointDto {
        return DataPointDto(
            id = entity.id.value,
            workflow = workflowMapper.toDto(entity.workflow),
            name = entity.name,
            presetValue = entity.presetValue,
            storeMethod = entity.storeMethod.toDto(),
            typeDeclaration = typeDeclarationMapper.toDto(entity.typeDeclaration),
            description = entity.description,
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
        )
    }
}