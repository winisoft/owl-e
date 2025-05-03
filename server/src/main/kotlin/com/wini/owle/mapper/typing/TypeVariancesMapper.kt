package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeVariance
import com.wini.owle.dto.typing.enums.VarianceCardinalityDto
import com.wini.owle.entity.typing.enums.VarianceCardinalityEntity
import com.wini.owle.entity.typing.TypeVarianceEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class TypeVariancesMapper(
    private val daoFactory: DaoFactory<TypeVarianceEntity, Long>,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
): DbMapper<TypeVariance, TypeVarianceEntity>() {

    override fun toEntity(dto: TypeVariance): TypeVarianceEntity {
        val applyProps: TypeVarianceEntity.() -> Unit = {
            typeVariableDeclaration = typeDeclarationsMapper.toEntity(dto.typeVariableDeclaration)
            varianceCardinality = when (dto.varianceCardinality) {
                VarianceCardinalityDto.COVARIANT -> VarianceCardinalityEntity.COVARIANT
                VarianceCardinalityDto.CONTRAVARIANT -> VarianceCardinalityEntity.CONTRAVARIANT
                VarianceCardinalityDto.INVARIANT -> VarianceCardinalityEntity.INVARIANT
            }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeVarianceEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeVariance with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeVarianceEntity): TypeVariance {
        return TypeVariance(
            id = entity.id.value,
            typeVariableDeclaration = typeDeclarationsMapper.toDto(entity.typeVariableDeclaration),
            varianceCardinality = when (entity.varianceCardinality) {
                VarianceCardinalityEntity.COVARIANT -> VarianceCardinalityDto.COVARIANT
                VarianceCardinalityEntity.CONTRAVARIANT -> VarianceCardinalityDto.CONTRAVARIANT
                VarianceCardinalityEntity.INVARIANT -> VarianceCardinalityDto.INVARIANT
            }
        )
    }
}