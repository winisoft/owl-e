package com.wini.owle.mapper.workflows

import com.wini.owle.dto.ToolDto
import com.wini.owle.entity.workflows.ToolDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper


class ToolsMapper(
    private val typeDeclarationMapper: TypeDeclarationsMapper
) : DbMapper<ToolDto, ToolDao>() {

    override val applyProps: ToolDao.(ToolDto) -> Unit = { dto ->
        name = dto.name
        description = dto.description
        body = dto.body
        returnTypeDeclaration = typeDeclarationMapper.run { dto.returnTypeDeclaration?.toEntity() }
    }

    override fun ToolDto.toEntity(): ToolDao {
        return if (id == 0L) {
            ToolDao.new { applyProps(this@toEntity) }
        } else {
            ToolDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Tool with id=$id not found")
        }
    }

    override fun ToolDao.toDto(): ToolDto {
        return ToolDto(
            id = id.value,
            name = name,
            description = description,
            body = body,
            returnTypeDeclaration = typeDeclarationMapper.run {
                returnTypeDeclaration?.toDto()
            }
        )
    }
}