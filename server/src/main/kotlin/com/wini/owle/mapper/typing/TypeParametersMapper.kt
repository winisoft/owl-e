package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeParameterDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeParameters
import com.wini.owle.entity.typing.TypeParameterDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TypeParametersMapper(
    private val typeDeclarationsMapper: TypeDeclarationsMapper
) : DbMapper<TypeParameterDto, TypeParameterDao>() {

    override val applyProps: TypeParameterDao.(TypeParameterDto) -> Unit = { dto ->
        typeDeclaration = typeDeclarationsMapper.run { dto.typeDeclaration.toEntity() }
        position = dto.position
        parameterTypeDeclaration = typeDeclarationsMapper.run { dto.parameterTypeDeclaration.toEntity() }
    }

    override fun TypeParameterDto.toEntity(): TypeParameterDao {
        return if (id == 0L) {
            TypeParameterDao.new { applyProps(this@toEntity) }
        } else {
            TypeParameterDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TypeParameter with id=$id not found")
        }
    }

    override fun TypeParameterDao.toDto(): TypeParameterDto {
        return TypeParameterDto(
            id = id.value,
            typeDeclaration = typeDeclarationsMapper.run { typeDeclaration.toDto() },
            position = position,
            parameterTypeDeclaration = typeDeclarationsMapper.run { parameterTypeDeclaration.toDto() }
        )
    }
}