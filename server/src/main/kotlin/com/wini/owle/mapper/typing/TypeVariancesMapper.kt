package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeVarianceDto
import com.wini.owle.entity.VarianceCardinality
import com.wini.owle.entity.environs.PackageRepoDao
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
                com.wini.owle.dto.enums.VarianceCardinality.Covariant -> VarianceCardinality.COVARIANT
                com.wini.owle.dto.enums.VarianceCardinality.Contravariant -> VarianceCardinality.CONTRAVARIANT
                com.wini.owle.dto.enums.VarianceCardinality.Invariant -> VarianceCardinality.INVARIANT
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
                VarianceCardinality.COVARIANT -> com.wini.owle.dto.enums.VarianceCardinality.Covariant
                VarianceCardinality.CONTRAVARIANT -> com.wini.owle.dto.enums.VarianceCardinality.Contravariant
                VarianceCardinality.INVARIANT -> com.wini.owle.dto.enums.VarianceCardinality.Invariant
            }
        )
    }
}