package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeVarianceDto
import com.wini.owle.dto.typing.VarianceCardinalityDto
import com.wini.owle.entity.typing.enums.VarianceCardinality
import com.wini.owle.entity.typing.TypeVarianceDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class TypeVariancesMapper(
    private val daoFactory: DaoFactory<TypeVarianceDao, Long>,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
): DbMapper<TypeVarianceDto, TypeVarianceDao>() {

    override fun toEntity(dto: TypeVarianceDto): TypeVarianceDao {
        val applyProps: TypeVarianceDao.() -> Unit = {
            typeVariableDeclaration = typeDeclarationsMapper.toEntity(dto.typeVariableDeclaration)
            varianceCardinality = when (dto.varianceCardinality) {
                VarianceCardinalityDto.Covariant -> VarianceCardinality.COVARIANT
                VarianceCardinalityDto.Contravariant -> VarianceCardinality.CONTRAVARIANT
                VarianceCardinalityDto.Invariant -> VarianceCardinality.INVARIANT
            }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeVarianceDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeVariance with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeVarianceDao): TypeVarianceDto {
        return TypeVarianceDto(
            id = entity.id.value,
            typeVariableDeclaration = typeDeclarationsMapper.toDto(entity.typeVariableDeclaration),
            varianceCardinality = when (entity.varianceCardinality) {
                VarianceCardinality.COVARIANT -> VarianceCardinalityDto.Covariant
                VarianceCardinality.CONTRAVARIANT -> VarianceCardinalityDto.Contravariant
                VarianceCardinality.INVARIANT -> VarianceCardinalityDto.Invariant
            }
        )
    }
}