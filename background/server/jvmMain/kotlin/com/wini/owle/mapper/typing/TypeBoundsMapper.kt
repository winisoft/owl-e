package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeBoundsDto
import com.wini.owle.entity.TypeBounds
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.typing.TypeBoundDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TypeBoundsMapper(
    private val typeDeclarationMapper: TypeDeclarationsMapper
) : DbMapper<TypeBoundsDto, TypeBoundDao>() {

    override val applyProps: TypeBoundDao.(TypeBoundsDto) -> Unit = { dto ->
        typeVariableDeclaration = typeDeclarationMapper.run { dto.typeVariableDeclaration.toEntity() }
        boundTypeDeclaration = typeDeclarationMapper.run { dto.boundTypeDeclaration.toEntity() }
    }

    override fun TypeBoundsDto.toEntity(): TypeBoundDao {
        return if (id == 0L) {
            TypeBoundDao.new { applyProps(this@toEntity) }
        } else {
            TypeBoundDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TypeBound with id=$id not found")
        }
    }

    override fun TypeBoundDao.toDto(): TypeBoundsDto {
        return TypeBoundsDto(
            id = id.value,
            typeVariableDeclaration = typeDeclarationMapper.run { typeVariableDeclaration.toDto() },
            boundTypeDeclaration = typeDeclarationMapper.run { boundTypeDeclaration.toDto() }
        )
    }
}