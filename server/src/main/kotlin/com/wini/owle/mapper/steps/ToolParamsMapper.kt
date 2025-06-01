package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ToolParam
import com.wini.owle.entity.steps.ToolParamEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeNodeMapper
import com.wini.owle.mapper.workflows.ToolMapper

class ToolParamsMapper(
    private val daoDelegate: DaoDelegate<ULong, ToolParam, ToolParamEntity>,
    private val typeNodeMapper: TypeNodeMapper,
    private val toolMapper: ToolMapper
): DbMapper<ULong, ToolParam, ToolParamEntity> {

    override fun toEntity(dto: ToolParam): ToolParamEntity {
        return daoDelegate.findOrCreate(dto) {
            name = dto.name
            typeNode = typeNodeMapper.toEntity(dto.typeDeclaration)
            tool = toolMapper.toEntity(dto.tool)
        }
    }

    override fun toDto(entity: ToolParamEntity): ToolParam {
        return ToolParam(
            id = entity.id.value,
            name = entity.name,
            typeDeclaration = typeNodeMapper.toDto(entity.typeNode),
            tool = toolMapper.toDto(entity.tool)
        )
    }
}