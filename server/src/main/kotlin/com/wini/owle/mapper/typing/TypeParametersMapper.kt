package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeParameterDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeParameters
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.typing.TypeParameterDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TypeParametersMapper(
    private val daoFactory: DaoFactory<TypeParameterDao, Long>,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
): DbMapper<TypeParameterDto, TypeParameterDao>() {

    override fun toEntity(dto: TypeParameterDto): TypeParameterDao {
        val applyProps: TypeParameterDao.() -> Unit = {
            typeDeclaration = typeDeclarationsMapper.toEntity(dto.typeDeclaration)
            position = dto.position
            parameterTypeDeclaration = typeDeclarationsMapper.toEntity(dto.parameterTypeDeclaration)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeParameterDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeParameter with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeParameterDao): TypeParameterDto {
        return TypeParameterDto(
            id = entity.id.value,
            typeDeclaration = typeDeclarationsMapper.toDto(entity.typeDeclaration),
            position = entity.position,
            parameterTypeDeclaration = typeDeclarationsMapper.toDto(entity.parameterTypeDeclaration)
        )
    }
}