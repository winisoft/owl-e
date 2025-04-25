package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Tool
import com.wini.owle.entity.workflows.ToolEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper


class ToolsMapper(
    private val daoFactory: DaoFactory<ToolEntity, Long>,
    private val typeDeclarationMapper: TypeDeclarationsMapper
) : DbMapper<Tool, ToolEntity>() {

    override fun toEntity(dto: Tool): ToolEntity {
        val applyProps: ToolEntity.() -> Unit = {
            name = dto.name
            description = dto.description
            body = dto.body
            returnTypeDeclarationEntity = dto.returnTypeDeclaration?.let { typeDeclarationMapper.run { toEntity(it) } }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ToolEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Tool with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ToolEntity): Tool {
        return Tool(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            body = entity.body,
            returnTypeDeclaration = entity.returnTypeDeclarationEntity?.let { typeDeclarationMapper.toDto(it) }
        )
    }

}