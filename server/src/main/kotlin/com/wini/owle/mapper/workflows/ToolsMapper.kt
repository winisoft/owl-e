package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.ToolDto
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper


class ToolsMapper(
    private val daoFactory: DaoFactory<ToolDao, Long>,
    private val typeDeclarationMapper: TypeDeclarationsMapper,
    private val toolParamMapper: ToolParamsMapper
) : DbMapper<ToolDto, ToolDao>() {

    override fun toEntity(dto: ToolDto): ToolDao {
        val applyProps: ToolDao.() -> Unit = {
            name = dto.name
            description = dto.description
            body = dto.body
            returnTypeDeclaration = dto.returnTypeDeclaration?.let { typeDeclarationMapper.run { toEntity(it) } }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ToolDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Tool with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ToolDao): ToolDto {
        return ToolDto(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            body = entity.body,
            returnTypeDeclaration = entity.returnTypeDeclaration?.let { typeDeclarationMapper.toDto(it) }
        )
    }

}