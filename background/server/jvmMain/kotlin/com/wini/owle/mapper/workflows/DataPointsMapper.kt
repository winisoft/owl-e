package com.wini.owle.mapper.workflows

import com.wini.owle.dto.DataPointDto
import com.wini.owle.entity.DataPoints
import com.wini.owle.entity.StoreMethod
import com.wini.owle.entity.StoreMethod.Companion.toDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import org.jetbrains.exposed.dao.id.EntityID


class DataPointsMapper(
    val typeDeclarationMapper: TypeDeclarationsMapper,
    val workflowMapper: WorkflowsMapper
) : DbMapper<DataPointDto, DataPointDao>() {

    override val applyProps: DataPointDao.(DataPointDto) -> Unit = { dto ->
        workflow = workflowMapper.run { dto.workflow.toEntity() }
        name = dto.name
        presetValue = dto.presetValue
        storeMethod = StoreMethod.fromDto(dto.storeMethod)
        typeDeclaration = typeDeclarationMapper.run { dto.typeDeclaration.toEntity() }
    }

    override fun DataPointDto.toEntity(): DataPointDao {
        return if (id == 0L) {
            DataPointDao.new { applyProps(this@toEntity) }
        } else {
            DataPointDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("DataPoint with id=$id not found")
        }
    }

    override fun DataPointDao.toDto(): DataPointDto {
        return DataPointDto(
            id = id.value,
            workflow = workflowMapper.run { workflow.toDto() },
            name = name,
            presetValue = presetValue,
            storeMethod = storeMethod.toDto(),
            typeDeclaration = typeDeclarationMapper.run { typeDeclaration.toDto() },
            description = description
        )
    }
}