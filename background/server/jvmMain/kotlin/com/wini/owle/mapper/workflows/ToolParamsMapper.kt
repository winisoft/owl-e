package com.wini.owle.mapper.workflows

import com.wini.owle.dto.ToolParamDto
import com.wini.owle.entity.ToolParams
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import org.jetbrains.exposed.dao.id.EntityID


class ToolParamsMapper(
    private val typeDeclarationsMapper: TypeDeclarationsMapper,
    private val toolMapper: ToolsMapper
) : DbMapper<ToolParamDto, ToolParamDao>() {

    override val applyProps: ToolParamDao.(ToolParamDto) -> Unit = { dto ->
        name = dto.name
        typeDeclaration = typeDeclarationsMapper.run { dto.typeDeclaration.toEntity() }
        hasDefault = dto.hasDefault
    }

    override fun ToolParamDto.toEntity(): ToolParamDao {
        return if (id == 0L) {
            ToolParamDao.new { applyProps(this@toEntity) }
        } else {
            ToolParamDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("ToolParam with id=$id not found")
        }
    }

    override fun ToolParamDao.toDto(): ToolParamDto {
        return ToolParamDto(
            id = id.value,
            name = name,
            typeDeclaration = typeDeclarationsMapper.run { typeDeclaration.toDto() },
            hasDefault = hasDefault,
            tool = toolMapper.run { tool.toDto() }
        )
    }
}