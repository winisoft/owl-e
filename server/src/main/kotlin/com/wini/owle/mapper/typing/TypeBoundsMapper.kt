package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeBoundsDto
import com.wini.owle.entity.TypeBounds // Table definition, keep if needed
import com.wini.owle.entity.TypeDeclarations // Table definition, keep if needed
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.typing.TypeBoundDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TypeBoundsMapper(
    private val daoFactory: DaoFactory<TypeBoundDao, Long>,
    private val typeDeclarationMapper: TypeDeclarationsMapper
): DbMapper<TypeBoundsDto, TypeBoundDao>() {

    override fun toEntity(dto: TypeBoundsDto): TypeBoundDao {
        val applyProps: TypeBoundDao.() -> Unit = {
            typeVariableDeclaration = typeDeclarationMapper.toEntity(dto.typeVariableDeclaration)
            boundTypeDeclaration = typeDeclarationMapper.toEntity(dto.boundTypeDeclaration)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeBoundDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeBound with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeBoundDao): TypeBoundsDto {
        return TypeBoundsDto(
            id = entity.id.value,
            typeVariableDeclaration = typeDeclarationMapper.toDto(entity.typeVariableDeclaration),
            boundTypeDeclaration = typeDeclarationMapper.toDto(entity.boundTypeDeclaration)
        )
    }
}