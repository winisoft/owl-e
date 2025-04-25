package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.ToolParam
import com.wini.owle.entity.workflows.ToolParamEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper


class ToolParamsMapper(
    private val daoFactory: DaoFactory<ToolParamEntity, Long>,
    private val typeDeclarationsMapper: TypeDeclarationsMapper,
    private val toolMapper: ToolsMapper
) : DbMapper<ToolParam, ToolParamEntity>() {

    override fun toEntity(dto: ToolParam): ToolParamEntity {
        val applyProps: ToolParamEntity.() -> Unit = {
            name = dto.name
            typeDeclarationEntity = typeDeclarationsMapper.toEntity(dto.typeDeclaration)
            hasDefault = dto.hasDefault
            tool = toolMapper.toEntity(dto.tool)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ToolParamEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("ToolParam with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ToolParamEntity): ToolParam {
        return ToolParam(
            id = entity.id.value,
            name = entity.name,
            typeDeclaration = typeDeclarationsMapper.toDto(entity.typeDeclarationEntity),
            hasDefault = entity.hasDefault,
            tool = toolMapper.toDto(entity.tool)
        )
    }
}
