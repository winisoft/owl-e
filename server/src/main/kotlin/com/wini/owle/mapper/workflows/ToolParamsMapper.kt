package com.wini.owle.mapper.workflows

import com.wini.owle.dto.ToolParamDto
import com.wini.owle.entity.ToolParams
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.workflows.ToolParamDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import org.jetbrains.exposed.dao.id.EntityID


class ToolParamsMapper(
    private val daoFactory: DaoFactory<ToolParamDao, Long>,
    private val typeDeclarationsMapper: TypeDeclarationsMapper,
    private val toolMapper: ToolsMapper
) : DbMapper<ToolParamDto, ToolParamDao>() {

    override fun toEntity(dto: ToolParamDto): ToolParamDao {
        val applyProps: ToolParamDao.() -> Unit = {
            name = dto.name
            typeDeclaration = typeDeclarationsMapper.toEntity(dto.typeDeclaration)
            hasDefault = dto.hasDefault
            tool = toolMapper.toEntity(dto.tool)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ToolParamDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("ToolParam with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ToolParamDao): ToolParamDto {
        return ToolParamDto(
            id = entity.id.value,
            name = entity.name,
            typeDeclaration = typeDeclarationsMapper.toDto(entity.typeDeclaration),
            hasDefault = entity.hasDefault,
            tool = toolMapper.toDto(entity.tool)
        )
    }
}
