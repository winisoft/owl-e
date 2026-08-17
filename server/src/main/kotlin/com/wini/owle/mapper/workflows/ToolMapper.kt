package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Tool
import com.wini.owle.entity.workflows.ToolEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeNodeMapper


class ToolMapper(
    private val daoDelegate: DaoDelegate<ULong, Tool, ToolEntity>,
    private val typeDeclarationMapper: TypeNodeMapper
): DbMapper<ULong, Tool, ToolEntity> {

    override fun toEntity(dto: Tool): ToolEntity {
        return daoDelegate.findOrCreate(dto) {
            name = dto.name
            description = dto.description
            body = dto.body
            returnTypeNode = dto.returnTypeNode?.let { typeDeclarationMapper.run { toEntity(it) } }
        }
    }

    override fun toDto(entity: ToolEntity): Tool {
        return Tool(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            body = entity.body,
            returnTypeDeclaration = entity.returnTypeNode?.let { typeDeclarationMapper.toDto(it) }
        )
    }

}